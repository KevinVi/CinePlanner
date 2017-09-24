package com.example.cinemaplanner.team.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.exceptions.MustBeAuthenticatedException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.mail.MailService;
import com.example.cinemaplanner.team.model.*;
import com.example.cinemaplanner.team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Kevin on 06/08/2017 for ZKY.
 */

@RestController
@CrossOrigin
@RequestMapping("/team")
public class TeamController {
    private final TeamRepository teamRepository;
    private final AccountService accountService;
    private final MailService emailService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public TeamController(TeamRepository teamRepository, AccountService accountService, MailService emailService, AuthenticationManager authenticationManager) {
        this.teamRepository = teamRepository;
        this.accountService = accountService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "create", method = POST)
    public TeamPublic createTeam(@RequestHeader(value = "token") String token, @RequestBody TeamCreate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (info.getName().isEmpty()) {
            throw new Exception("Can't be empty");
        }
        if (account != null) {
            System.out.println(account.getTeams().toString());
            for (Team g :
                    account.getTeams()) {
                if (g.getName().equals(info.getName())) {
                    if (g.getCreator().equals(account.getLogin())) {
                        throw new Exception("You have a team with this name already");
                    }
                }
            }
            Team team = new Team();
            team.setName(info.getName());
            team.setCreator(account.getLogin());
            if (info.getUsers()!=null) {
                team.setPendingUsers(info.getUsers());
            }
            account.getTeams().add(team);

            if (info.getUsers() != null) {
                for (String user : info.getUsers()) {
                    Account guest = accountService.getAccountByLogin(user);
                    if (guest != null) {
                        emailService.addToTeam(guest.getLogin(), "localhost:8080/");
                    } else {
                        emailService.addToTeamNewAccount(user, "localhost:8080/");
                        //send mail to create and invite using creator and name
                    }
                }
            }
            teamRepository.save(team);
            accountService.saveAccount(account);

            System.out.println(team);
            return new TeamPublic(team);
        } else {
            throw new MustBeAuthenticatedException();
        }

    }

    @RequestMapping(value = "leave", method = POST)
    public boolean leaveTeam(@RequestHeader(value = "token") String token, @RequestBody TeamId id) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            for (Team g :
                    account.getTeams()) {
                if (g.getId() == id.getId()) {
                    account.getTeams().remove(g);
                    accountService.saveAccount(account);
                    return true;
                }
            }
            return false;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "teams", method = POST)
    public List<TeamPublic> getTeams(@RequestHeader(value = "token") String token) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            List<TeamPublic> teamPublics = new ArrayList<>();
            for (Team g :
                    account.getTeams()) {
                teamPublics.add(new TeamPublic(g));
            }
            return teamPublics;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "invite", method = POST)
    public boolean inviteToTeam(@RequestHeader(value = "token") String token, @RequestBody TeamContent invite) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            Account guest = accountService.getAccountByLogin(invite.getString());
            if (guest != null) {
                //send mail to notify invite using creator and name
                return true;
            } else {
                //send mail to create and invite using creator and name
                return true;
            }
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "pending", method = POST)
    public List<TeamPublic> pendingTeams(@RequestHeader(value = "token") String token) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            List<Team> teams = teamRepository.findByLogin(account.getLogin());
            List<TeamPublic> teamPublics = new ArrayList<>();
            for (Team t :
                    teams) {
                teamPublics.add(new TeamPublic(t));
            }
            return teamPublics;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }


    @RequestMapping(value = "join", method = POST)
    public boolean pendingTeams(@RequestHeader(value = "token") String token, @RequestBody TeamId id) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            Team team = teamRepository.findById(id.getId());
            if (team.getPendingUsers().contains(account.getLogin())) {
                team.getPendingUsers().remove(account.getLogin());
            } else {
                throw new Exception("you are not in this team");
            }
            account.getTeams().add(team);
            teamRepository.save(team);
            accountService.saveAccount(account);
            return true;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

}

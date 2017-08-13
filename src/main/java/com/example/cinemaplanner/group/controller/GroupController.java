package com.example.cinemaplanner.group.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.exceptions.MustBeAuthenticatedException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.group.model.*;
import com.example.cinemaplanner.group.repository.GroupRepository;
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
@RequestMapping("/group")
public class GroupController {
    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public GroupController(GroupRepository groupRepository, AccountService accountService, AuthenticationManager authenticationManager) {
        this.groupRepository = groupRepository;
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "create", method = POST)
    public GroupPublic createGroup(@RequestHeader(value = "token") String token, @RequestBody GroupCreate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (info.getName().isEmpty()) {
            throw new Exception("Can't be empty");
        }
        if (account != null) {
            System.out.println(account.getGroup().toString());
            for (Group g :
                    account.getGroup()) {
                if (g.getName().equals(info.getName())) {
                    if (g.getCreator().equals(account.getLogin())) {
                        throw new Exception("You have a group with this name already");
                    }
                }
            }
            Group group = new Group();
            group.setName(info.getName());
            group.setCreator(account.getLogin());
            group.setPendingUsers(info.getUsers());

            for (String user : info.getUsers()) {
                Account guest = accountService.getAccountByLogin(user);
                if (guest != null) {
                    //send mail to notify invite using creator and name
                } else {
                    //send mail to create and invite using creator and name
                }
            }
            groupRepository.save(group);

            return new GroupPublic(group);
        } else {
            throw new MustBeAuthenticatedException();
        }

    }

    @RequestMapping(value = "leave", method = POST)
    public boolean leaveGroup(@RequestHeader(value = "token") String token, @RequestBody GroupId id) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            for (Group g :
                    account.getGroup()) {
                if (g.getId() == id.getId()) {
                    account.getGroup().remove(g);
                    accountService.updateAccount(account);
                    return true;
                }
            }
            return false;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "groups", method = POST)
    public List<GroupPublic> getGroups(@RequestHeader(value = "token") String token) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            List<GroupPublic> groupPublics = new ArrayList<>();
            for (Group g :
                    account.getGroup()) {
                groupPublics.add(new GroupPublic(g));
            }
            return groupPublics;
        } else {
            throw new MustBeAuthenticatedException();
        }
    }

    @RequestMapping(value = "invite", method = POST)
    public boolean inviteToGroup(@RequestHeader(value = "token") String token, @RequestBody GroupContent invite) {
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


}

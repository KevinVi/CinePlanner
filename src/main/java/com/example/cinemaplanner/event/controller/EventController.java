package com.example.cinemaplanner.event.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.exceptions.AccountNotFoundException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.event.model.Event;
import com.example.cinemaplanner.event.model.EventCreate;
import com.example.cinemaplanner.event.model.EventPublic;
import com.example.cinemaplanner.event.model.EventUpdate;
import com.example.cinemaplanner.event.repository.EventRepository;
import com.example.cinemaplanner.team.model.Team;
import com.example.cinemaplanner.team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
 */

@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController {
    private final TeamRepository teamRepository;
    private final AccountService accountService;
    private final EventRepository eventRepository;
    private final AuthenticationManager authenticationManager;
    private final long minDate = 1400000000000L;


    @Autowired
    public EventController(TeamRepository teamRepository, AccountService accountService, EventRepository eventRepository, AuthenticationManager authenticationManager) {
        this.teamRepository = teamRepository;
        this.accountService = accountService;
        this.eventRepository = eventRepository;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "create", method = POST)
    public EventPublic createEvent(@RequestHeader(value = "token") String token, @RequestBody EventCreate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (info.getEnd() < minDate || info.getStart() < minDate || info.getName().isEmpty()) {
            throw new Exception("Wrong parameters");
        } else {
            if (account != null) {
                for (Team team :
                        account.getTeams()) {
                    if (team.getId() == info.getIdTeam()) {
                        Event event = new Event();
                        event.setName(info.getName());
                        event.setDtend(info.getEnd());
                        event.setDtstart(info.getStart());
                        event.setCreator(account.getFirstName());
                        eventRepository.save(event);
                        List<Event> events = team.getEvents();
                        events.add(event);
                        team.setEvents(events);
                        teamRepository.save(team);
                        return new EventPublic(event);
                    }
                }
                throw new Exception("Wrong team id");
            } else {
                throw new AccountNotFoundException();
            }
        }
    }

    @RequestMapping(value = "update", method = POST)
    public EventPublic updateEvent(@RequestHeader(value = "token") String token, @RequestBody EventUpdate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);


        if (account != null) {
            Event event = eventRepository.findById(info.getId());

            if (event != null) {
                if (info.getEnd() < minDate && info.getStart() < minDate && info.getName().isEmpty()) {
                    return new EventPublic(event);
                }
                if (!info.getName().isEmpty()) {
                    event.setName(info.getName());
                }
                if (info.getStart() > minDate) {
                    event.setDtstart(info.getStart());
                }
                if (info.getEnd() > minDate) {
                    event.setDtend(info.getEnd());
                }
                eventRepository.save(event);
                return new EventPublic(event);
            }
            return new EventPublic();
        } else {
            throw new AccountNotFoundException();
        }
    }

}

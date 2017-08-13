package com.example.cinemaplanner.event.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.event.model.EventCreate;
import com.example.cinemaplanner.event.model.EventPublic;
import com.example.cinemaplanner.event.repository.EventRepository;
import com.example.cinemaplanner.group.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
 */

@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController {
    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final EventRepository eventRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public EventController(GroupRepository groupRepository, AccountService accountService, EventRepository eventRepository, AuthenticationManager authenticationManager) {
        this.groupRepository = groupRepository;
        this.accountService = accountService;
        this.eventRepository = eventRepository;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "create", method = POST)
    public EventPublic createEvent(@RequestHeader(value = "token") String token, @RequestBody EventCreate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (info.getEnd().isEmpty() || info.getStart().isEmpty() || info.getName().isEmpty()) {
            throw new Exception("Can't be empty");
        }
        if (account != null) {

        }
        return new EventPublic();
    }
}

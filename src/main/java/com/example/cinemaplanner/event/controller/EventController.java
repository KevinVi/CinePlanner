package com.example.cinemaplanner.event.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.event.model.Event;
import com.example.cinemaplanner.event.model.EventCreate;
import com.example.cinemaplanner.event.model.EventPublic;
import com.example.cinemaplanner.event.model.EventUpdate;
import com.example.cinemaplanner.event.repository.EventRepository;
import com.example.cinemaplanner.group.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    public EventPublic createEvent(@RequestHeader(value = "token") String token) throws Exception {
//        authenticationManager.mustBeValidToken(token);
//        Account account = authenticationManager.getAccountFromToken(token);
//        if (info.getEnd().isEmpty() || info.getStart().isEmpty() || info.getName().isEmpty()) {
//            throw new Exception("Can't be empty");
//        }
//        if (account != null) {
//
//        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return new EventPublic("test", "test", dateFormat.format(new Timestamp(System.currentTimeMillis()))
                , dateFormat.format(new Timestamp(1503858860508L)));
    }

    @RequestMapping(value = "update", method = POST)
    public EventPublic updateEvent(@RequestHeader(value = "token") String token, @RequestBody EventUpdate info) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);


        if (account != null) {
            Event event = eventRepository.findById(info.getId());

            if (event != null) {
                if (info.getEnd() != null && info.getStart() != null && info.getName().isEmpty()) {
                    return new EventPublic(event);
                }
                if (!info.getName().isEmpty()) {
                    event.setName(info.getName());
                }
                if (info.getStart() != null) {
                    event.setDtstart(info.getStart());
                }
                if (info.getEnd() != null) {
                    event.setDtend(info.getEnd());
                }
                return new EventPublic(event);
            }
        }
        return new EventPublic();
    }

}

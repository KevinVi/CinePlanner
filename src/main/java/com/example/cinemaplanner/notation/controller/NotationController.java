package com.example.cinemaplanner.notation.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.exceptions.AccountNotFoundException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.event.model.event.Event;
import com.example.cinemaplanner.event.repository.EventRepository;
import com.example.cinemaplanner.notation.model.Notation;
import com.example.cinemaplanner.notation.model.NotationCreate;
import com.example.cinemaplanner.notation.repository.NotationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Kevin on 01/10/2017 for CinePlanner.
 */
@RestController
@CrossOrigin
@RequestMapping("/notation")
public class NotationController {
    private final NotationRepository notationRepository;
    private final EventRepository eventRepository;
    private final AuthenticationManager authenticationManager;

    public NotationController(NotationRepository notationRepository, EventRepository eventRepository, AuthenticationManager authenticationManager) {
        this.notationRepository = notationRepository;
        this.eventRepository = eventRepository;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "create", method = POST)
    public Notation createNotation(@RequestHeader(value = "token") String token, @RequestBody NotationCreate notation) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            Event event = eventRepository.findById(notation.getIdEvent());
            if (event != null) {
                if (notation.getNotation() >= 0 && notation.getNotation() <= 5) {
                    Notation notation1 = new Notation();
                    notation1.setAuthorId(account.getId());
                    notation1.setEventId(event.getId());
                    notation1.setNotation(notation.getNotation());
                    List<Notation> notations = event.getNotations();
                    notations.add(notation1);
                    event.setNotations(notations);
                    notationRepository.save(notation1);
                    eventRepository.save(event);
                    return notation1;
                } else {
                    throw new Exception("Notation incorrect");
                }
            } else {
                throw new Exception("Wrong event id");
            }
        } else {
            throw new AccountNotFoundException();
        }
    }
}

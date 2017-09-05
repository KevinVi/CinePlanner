package com.example.cinemaplanner.comment.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.comment.repository.CommentRepository;
import com.example.cinemaplanner.event.repository.EventRepository;
import com.example.cinemaplanner.team.repository.TeamRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kevin on 05/09/2017 for ZKY.
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    private final TeamRepository teamRepository;
    private final AccountService accountService;
    private final EventRepository eventRepository;
    private final AuthenticationManager authenticationManager;
    private final CommentRepository commentRepository;
    private final long minDate = 1400000000000L;

    public CommentController(TeamRepository teamRepository, AccountService accountService, EventRepository eventRepository, AuthenticationManager authenticationManager, CommentRepository commentRepository) {
        this.teamRepository = teamRepository;
        this.accountService = accountService;
        this.eventRepository = eventRepository;
        this.authenticationManager = authenticationManager;
        this.commentRepository = commentRepository;
    }
}

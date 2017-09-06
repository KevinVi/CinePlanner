package com.example.cinemaplanner.comment.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.exceptions.AccountNotFoundException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.comment.model.Comment;
import com.example.cinemaplanner.comment.model.CommentCreate;
import com.example.cinemaplanner.comment.model.CommentPublic;
import com.example.cinemaplanner.comment.model.CommentUpdate;
import com.example.cinemaplanner.comment.repository.CommentRepository;
import com.example.cinemaplanner.event.model.Event;
import com.example.cinemaplanner.event.repository.EventRepository;
import com.example.cinemaplanner.team.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(value = "create", method = POST)
    public CommentPublic createComment(@RequestHeader(value = "token") String token, @RequestBody CommentCreate comment) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            Event event = eventRepository.findById(comment.getIdEvent());
            if (event != null) {
                if (comment.getDateCreated() < minDate) {
                    throw new Exception("Wrong date");
                }
                Comment comment1 = new Comment();
                comment1.setAuthor(account.getLogin());
                comment1.setComment(comment.getComment());
                comment1.setDateCreated(comment.getDateCreated());
                comment1.setNotation(comment.getNotation());
                if (comment.isPreComment()) {
                    List<Comment> comments = event.getPreComment();
                    comments.add(comment1);
                    event.setPreComment(comments);
                } else {
                    List<Comment> comments = event.getPostComment();
                    comments.add(comment1);
                    event.setPostComment(comments);
                }
                commentRepository.save(comment1);
                eventRepository.save(event);
                return new CommentPublic(comment1);
            } else {
                throw new Exception("Wrong event id");
            }
        } else {
            throw new AccountNotFoundException();
        }
    }

    @RequestMapping(value = "update", method = POST)
    public CommentPublic updateEvent(@RequestHeader(value = "token") String token, @RequestBody CommentUpdate commentUpdate) throws Exception {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (account != null) {
            Comment comment = commentRepository.findById(commentUpdate.getId());

            if (comment != null) {
                if (commentUpdate.getComment().isEmpty()) {
                    comment.setComment(commentUpdate.getComment());
                }
                comment.setNotation(commentUpdate.getNotation());

                commentRepository.save(comment);
                return new CommentPublic(comment);
            }

            return new CommentPublic();
        } else {
            throw new AccountNotFoundException();
        }
    }

}

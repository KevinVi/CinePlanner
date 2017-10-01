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

import java.util.Calendar;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Kevin on 05/09/2017 for ZKY.
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    private final EventRepository eventRepository;
    private final AuthenticationManager authenticationManager;
    private final CommentRepository commentRepository;

    public CommentController(EventRepository eventRepository, AuthenticationManager authenticationManager, CommentRepository commentRepository) {
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
                if (comment.getComment() != null) {
                    if (!comment.getComment().isEmpty()) {
                        Comment comment1 = new Comment();
                        comment1.setAuthor(account.getLogin());
                        comment1.setComment(comment.getComment());
                        Calendar calendar = Calendar.getInstance();
                        comment1.setDateCreated(calendar.getTime().getTime());
                        comment1.setComment(comment.getComment());
                        List<Comment> comments = event.getComments();
                        comments.add(comment1);
                        event.setComments(comments);
                        commentRepository.save(comment1);
                        eventRepository.save(event);
                        return new CommentPublic(comment1);
                    } else {
                        throw new Exception("Comment is empty");
                    }
                } else {
                    throw new Exception("Comment is null");
                }
            } else {
                throw new Exception("Wrong event id");
            }
        } else {
            throw new AccountNotFoundException();
        }
    }


}

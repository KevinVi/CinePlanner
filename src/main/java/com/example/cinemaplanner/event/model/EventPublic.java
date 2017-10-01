package com.example.cinemaplanner.event.model;

import com.example.cinemaplanner.comment.model.Comment;
import com.example.cinemaplanner.comment.model.CommentPublic;
import com.example.cinemaplanner.notation.model.Notation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventPublic {

    long id;
    String name;
    String creator;
    int creatorId;
    long start;
    long end;
    List<CommentPublic> comments;
    List<Notation> notations;
    Movie movie;

    public EventPublic(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.creator = event.getCreator();
        this.start = event.getDtstart();
        this.end = event.getDtend();
        List<CommentPublic> commentPublics = new ArrayList<>();

        for (Comment c :
                event.getComments()) {
            commentPublics.add(new CommentPublic(c));
        }
        this.comments = commentPublics;
        this.notations = event.getNotations();
        this.movie = event.getDescription();
        this.creatorId = event.getCreatorId();
    }
}

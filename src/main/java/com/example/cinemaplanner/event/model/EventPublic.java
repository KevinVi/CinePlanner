package com.example.cinemaplanner.event.model;

import com.example.cinemaplanner.comment.model.Comment;
import com.example.cinemaplanner.comment.model.CommentPublic;
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

    String name;
    String creator;
    long start;
    long end;
    List<CommentPublic> preComments;
    List<CommentPublic> postComments;

    public EventPublic(Event event) {
        this.name = event.getName();
        this.creator = event.getCreator();
        this.start = event.getDtstart();
        this.end = event.getDtend();
        List<CommentPublic> commentPublics = new ArrayList<>();
        for (Comment c :
                event.getPreComment()) {
            commentPublics.add(new CommentPublic(c));
        }
        this.preComments = commentPublics;
        commentPublics = new ArrayList<>();
        for (Comment c :
                event.getPostComment()) {
            commentPublics.add(new CommentPublic(c));
        }
        this.postComments = commentPublics;
    }
}

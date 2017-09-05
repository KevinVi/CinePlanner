package com.example.cinemaplanner.comment.model;

import lombok.*;

/**
 * Created by Kevin on 05/09/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentPublic {

    String author;
    String comment;
    int notation;
    long dateCreated;

    public CommentPublic(Comment comment) {
        this.author = comment.getAuthor();
        this.comment = comment.getComment();
        this.notation = comment.getNotation();
        this.dateCreated = comment.getDateCreated();
    }
}

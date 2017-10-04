package com.example.cinemaplanner.comment.model;

import lombok.*;

/**
 * Created by Kevin on 05/09/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdate {
    int id;
    int notation;
    String comment;
}

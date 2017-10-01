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
public class CommentCreate {

    String comment;
    int idEvent;
}

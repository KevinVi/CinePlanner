package com.example.cinemaplanner.event.model;

import lombok.*;

/**
 * Created by Kevin on 30/09/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LearningContent {
    int idTeam ;
    int idMovie;
    boolean liked;
}

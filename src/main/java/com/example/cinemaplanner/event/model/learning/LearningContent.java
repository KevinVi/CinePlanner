package com.example.cinemaplanner.event.model.learning;

import lombok.*;

/**
 * Created by Kevin on 30/09/2017 for CinePlanner.
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

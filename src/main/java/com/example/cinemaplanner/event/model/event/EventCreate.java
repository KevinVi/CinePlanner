package com.example.cinemaplanner.event.model.event;

import lombok.*;

/**
 * Created by Kevin on 13/08/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventCreate {

    String name;
    long start;
    long end;
    int idTeam;
    int idMovie;

}

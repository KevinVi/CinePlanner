package com.example.cinemaplanner.event.model;

import lombok.*;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
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
}

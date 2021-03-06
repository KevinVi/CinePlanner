package com.example.cinemaplanner.event.model.event;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Kevin on 27/08/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdate {
    String name;
    long start;
    long end;
    int id;
    int idMovie;
}

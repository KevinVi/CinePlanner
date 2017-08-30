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
public class EventPublic {

    String name;
    String creator;
    long start;
    long end;

    public EventPublic(Event event) {
        this.name = event.getName();
        this.creator = event.getCreator();
        this.start = event.getDtstart();
        this.end = event.getDtend();
    }
}

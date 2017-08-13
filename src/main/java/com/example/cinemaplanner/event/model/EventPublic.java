package com.example.cinemaplanner.event.model;

import com.example.cinemaplanner.group.model.GroupPublic;
import lombok.*;

import javax.persistence.Entity;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EventPublic {

    String name;
    String creator;
    String start;
    String end;
    GroupPublic group;

    public EventPublic(Event event) {
        this.name = event.getName();
        this.creator = event.getCreator();
        this.start = event.getStart();
        this.end = event.getEnd();
        this.group = new GroupPublic(event.getGroup());
    }
}

package com.example.cinemaplanner.team.model;

import com.example.cinemaplanner.event.model.Event;
import com.example.cinemaplanner.event.model.EventPublic;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 06/08/2017 for ZKY.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamPublic {

    int id;
    String name;
    String creator;
    List<String> pendingUsers;
    List<EventPublic> events;

    public TeamPublic(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.creator = team.getCreator();
        this.pendingUsers = team.getPendingUsers();
        List<EventPublic> eventPublics = new ArrayList<>();
        for (Event ev :
                team.getEvents()) {
            eventPublics.add(new EventPublic(ev));
        }
        this.events = eventPublics;
    }
}

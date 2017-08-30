package com.example.cinemaplanner.team.model;

import com.example.cinemaplanner.event.model.Event;
import lombok.*;

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

    String name;
    String creator;
    List<String> pendingUsers;
    List<Event> events;

    public TeamPublic(Team team) {
        this.name = team.getName();
        this.creator = team.getCreator();
        this.pendingUsers = team.getPendingUsers();
        this.events = team.getEvents();
    }
}

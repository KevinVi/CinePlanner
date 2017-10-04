package com.example.cinemaplanner.team.model;

import com.example.cinemaplanner.event.model.Event;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Kevin on 06/08/2017 for CinePlanner.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "TEAM")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    String name;

    @Column(name = "creator")
    String creator;

    @ElementCollection
    @Column(name = "pendingUsers")
    List<String> pendingUsers;

    @OneToMany
    @Column(name = "events")
    List<Event> events;
}

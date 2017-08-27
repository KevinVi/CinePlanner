package com.example.cinemaplanner.group.model;

import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.event.model.Event;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Kevin on 06/08/2017 for ZKY.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TEAM")
public class Group {

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

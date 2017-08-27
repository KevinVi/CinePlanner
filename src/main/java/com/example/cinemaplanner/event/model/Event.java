package com.example.cinemaplanner.event.model;

import com.example.cinemaplanner.group.model.Group;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    String name;

    @Column(name = "creator")
    String creator;

    @Column(name = "datestart")
    Timestamp  dtstart;

    @Column(name = "dateend")
    Timestamp dtend;


}

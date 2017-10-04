package com.example.cinemaplanner.notation.model;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Kevin on 01/10/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTATION")
public class Notation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "authorId")
    int authorId;

    @Column(name = "notation")
    int  notation;

    @Column(name = "eventId")
    int eventId;
}

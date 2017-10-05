package com.example.cinemaplanner.event.model.learning;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Kevin on 30/09/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LEARNING")
public class Learning {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "movie")
    private int movieId;

    @Column(name = "gender1")
    private String gender1;

    @Column(name = "gender2")
    private String gender2;

    @Column(name = "gender3")
    private String gender3;

    @Column(name = "answer")
    private String answer;

    @Column(name = "team")
    private int teamId;
}

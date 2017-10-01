package com.example.cinemaplanner.event.model;

import com.example.cinemaplanner.comment.model.Comment;
import com.example.cinemaplanner.notation.model.Notation;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENT")
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
    long dtstart;

    @Column(name = "dateend")
    long dtend;

    @OneToMany
    @Column(name = "comments")
    List<Comment> comments;

    @OneToMany
    @Column(name = "notations")
    List<Notation> notations;

    @OneToOne
    @JoinColumn(name = "description")
    Movie description;

}

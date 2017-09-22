package com.example.cinemaplanner.comment.model;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Kevin on 05/09/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "author")
    String author;

    @Column(name = "datecreated")
    long  dateCreated;

    @Column(name = "notation")
    int notation;

    @Column(name = "text")
    String comment;


}

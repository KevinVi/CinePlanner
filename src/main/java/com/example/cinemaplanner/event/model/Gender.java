package com.example.cinemaplanner.event.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Kevin on 30/09/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GENDER")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ElementCollection
    @Column(name = "gender")
    private List<Integer> genre_ids;
}

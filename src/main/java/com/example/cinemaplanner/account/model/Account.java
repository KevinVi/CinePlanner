package com.example.cinemaplanner.account.model;

/**
 * Created by Andreï on 02/04/2017 for CinePlanner.
 * Object account with column db
 */

import com.example.cinemaplanner.team.model.Team;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @ManyToMany
    @Column(name = "teams")
    private List<Team> teams;
}

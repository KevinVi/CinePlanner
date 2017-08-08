package com.example.cinemaplanner.account.model;

/**
 * Created by Andreï on 02/04/2017 for ZKY.
 * Object account with column db
 */

import com.example.cinemaplanner.group.model.GroupModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNT")
public class Account {

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

    @OneToMany
    @Column(name = "group")
    private List<GroupModel> group;
}

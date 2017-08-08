package com.example.cinemaplanner.group.model;

import com.example.cinemaplanner.account.model.Account;
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
@Table(name = "GROUP")
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    String name;

    @Column(name = "creator")
    String creator;

    @OneToMany
    @Column(name = "users")
    List<Account> users;

    @ElementCollection
    @Column(name = "pendingUsers")
    List<String> pendingUsers;
}

package com.example.cinemaplanner.account.model;

import lombok.*;

/**
 * Created by Andreï on 15/04/2016 for CinePlanner.
 * Object clean returning only useful data.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AccountPublic {

    private int id;
    private String login;
    private String firstName;
    private String lastName;

    public AccountPublic( Account account )
    {
        this.id = account.getId();
        this.login = account.getLogin();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
    }
}

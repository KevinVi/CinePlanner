package com.example.cinemaplanner.account.model;

import lombok.*;

/**
 * Created by Kevin on 06/08/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BodyAccount {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
}

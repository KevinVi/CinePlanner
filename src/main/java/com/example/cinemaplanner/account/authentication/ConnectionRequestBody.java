package com.example.cinemaplanner.account.authentication;

import lombok.*;

/**
 * Created by Andreï on 10/04/2016 for CinePlanner.
 * Body object login and password
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionRequestBody {
    private String login;
    private String password;
}

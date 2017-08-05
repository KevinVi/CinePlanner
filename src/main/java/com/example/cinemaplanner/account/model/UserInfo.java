package com.example.cinemaplanner.account.model;

import lombok.*;

/**
 * Created by Kevin on 06/07/2017 for ZKY.
 * Body object change user info
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String firstname;
    private String lastname;
}

package com.example.cinemaplanner.account.model;

import lombok.*;

/**
 * Created by Kevin on 06/07/2017 for CinePlanner.
 * Body object passwords
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChange {
    String oldPassword;
    String newPassword;
}

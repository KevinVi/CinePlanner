package com.example.cinemaplanner.account.authentication;

import lombok.*;

/**
 * Created by Andreï on 10/04/2016 for CinePlanner.
 * Body object token.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String token;
}

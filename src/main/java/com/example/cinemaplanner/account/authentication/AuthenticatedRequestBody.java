package com.example.cinemaplanner.account.authentication;

import lombok.*;

/**
 * Created by Andreï on 11/04/2017 for CinePlanner.
 * Default body for retrieving data + token
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedRequestBody<T> {
    private T body;
    private String token;
}

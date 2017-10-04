package com.example.cinemaplanner.account.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Created by Kevin on 06/08/2017 for CinePlanner.
 */
@ResponseStatus(UNAUTHORIZED)
public class TokenExpiredException extends RuntimeException {
}

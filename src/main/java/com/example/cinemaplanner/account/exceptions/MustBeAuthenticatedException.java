package com.example.cinemaplanner.account.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * Created by Andreï on 11/04/2017 for CinePlanner.
 */
@ResponseStatus(FORBIDDEN)
public class MustBeAuthenticatedException extends RuntimeException{
}

package com.example.cinemaplanner.account.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by Andreï on 02/04/2017 for CinePlanner.
 */
@ResponseStatus(BAD_REQUEST)
public class AccountFieldNotValidException extends RuntimeException{
    public AccountFieldNotValidException(String message )
    {
        super( message );
    }
}

package com.example.cinemaplanner.account.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Created by Andreï on 11/04/2017 for ZKY.
 */
@ResponseStatus(BAD_REQUEST)
public class TokenNotValidException extends RuntimeException{
}

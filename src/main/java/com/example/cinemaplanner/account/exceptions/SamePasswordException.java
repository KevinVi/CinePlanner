package com.example.cinemaplanner.account.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/**
 * Created by Kevin on 11/07/2017 for CinePlanner.
 */
@ResponseStatus(NOT_ACCEPTABLE)
public class SamePasswordException extends RuntimeException{
}

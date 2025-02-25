package com.greatnex.semicolon_task.domain.exception;

import org.springframework.http.HttpStatus;

public class UserException extends CohortException {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, HttpStatus status) {
        super(message, status);

    }
}

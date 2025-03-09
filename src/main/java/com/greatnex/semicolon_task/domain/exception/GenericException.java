package com.greatnex.semicolon_task.domain.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends Exception {
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;


    public GenericException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }
}

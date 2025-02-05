package com.greatnex.semicolon_task.exception;

import org.springframework.http.HttpStatus;

public class CohortException extends Exception {
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;


    public CohortException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

    public CohortException(String message) {
        super(message);
    }

    public CohortException(String message, Throwable cause) {
        super(message, cause);
    }

    public CohortException(Throwable cause) {
        super(cause);
    }
}

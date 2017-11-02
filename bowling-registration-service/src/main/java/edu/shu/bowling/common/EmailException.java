package edu.shu.bowling.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class EmailException extends RuntimeException {

    public EmailException() {
        super();
    }

    public EmailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmailException(final String message) {
        super(message);
    }

    public EmailException(final Throwable cause) {
        super(cause);
    }

}
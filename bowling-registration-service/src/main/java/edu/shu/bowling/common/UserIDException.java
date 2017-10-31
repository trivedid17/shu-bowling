package edu.shu.bowling.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class UserIDException extends RuntimeException {

    public UserIDException() {
        super();
    }

    public UserIDException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserIDException(final String message) {
        super(message);
    }

    public UserIDException(final Throwable cause) {
        super(cause);
    }

}
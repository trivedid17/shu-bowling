package edu.shu.bowling.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class PasswordLengthException extends RuntimeException {

    public PasswordLengthException() {
        super();
    }

    public PasswordLengthException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PasswordLengthException(final String message) {
        super(message);
    }

    public PasswordLengthException(final Throwable cause) {
        super(cause);
    }

}
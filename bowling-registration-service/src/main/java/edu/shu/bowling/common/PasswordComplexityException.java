package edu.shu.bowling.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class PasswordComplexityException extends RuntimeException {

    public PasswordComplexityException() {
        super();
    }

    public PasswordComplexityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PasswordComplexityException(final String message) {
        super(message);
    }

    public PasswordComplexityException(final Throwable cause) {
        super(cause);
    }

}
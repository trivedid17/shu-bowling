package edu.shu.bowling.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException() {
        super();
    }

    public AccountAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyExistException(final String message) {
        super(message);
    }

    public AccountAlreadyExistException(final Throwable cause) {
        super(cause);
    }

}
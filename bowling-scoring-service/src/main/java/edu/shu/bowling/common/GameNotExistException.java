package edu.shu.bowling.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class GameNotExistException extends RuntimeException {

    public GameNotExistException() {
        super();
    }

    public GameNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GameNotExistException(final String message) {
        super(message);
    }

    public GameNotExistException(final Throwable cause) {
        super(cause);
    }

}
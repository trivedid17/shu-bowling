package edu.shu.bowling.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class GameNotActiveException extends RuntimeException {

    public GameNotActiveException() {
        super();
    }

    public GameNotActiveException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GameNotActiveException(final String message) {
        super(message);
    }

    public GameNotActiveException(final Throwable cause) {
        super(cause);
    }

}
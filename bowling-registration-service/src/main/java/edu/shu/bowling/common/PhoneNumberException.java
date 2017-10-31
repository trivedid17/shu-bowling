package edu.shu.bowling.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class PhoneNumberException extends RuntimeException {

    public PhoneNumberException() {
        super();
    }

    public PhoneNumberException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PhoneNumberException(final String message) {
        super(message);
    }

    public PhoneNumberException(final Throwable cause) {
        super(cause);
    }

}
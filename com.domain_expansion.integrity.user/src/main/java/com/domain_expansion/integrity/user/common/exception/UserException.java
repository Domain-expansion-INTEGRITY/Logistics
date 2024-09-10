package com.domain_expansion.integrity.user.common.exception;

import com.domain_expansion.integrity.user.common.message.ExceptionMessage;
import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {

    private final ExceptionMessage message;

    public UserException(ExceptionMessage message) {
        super(message.getMessage());
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return message.getHttpStatus();
    }

    public String getMessage() {
        return message.getMessage();
    }
}

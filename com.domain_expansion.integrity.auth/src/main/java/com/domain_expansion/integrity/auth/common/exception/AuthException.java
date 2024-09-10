package com.domain_expansion.integrity.auth.common.exception;


import com.domain_expansion.integrity.auth.common.message.ExceptionMessage;
import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException {

    private final ExceptionMessage message;

    public AuthException(ExceptionMessage message) {
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

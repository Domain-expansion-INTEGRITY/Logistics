package com.domain_expansion.integrity.ai.common.exception;

import com.domain_expansion.integrity.ai.common.message.ExceptionMessage;
import org.springframework.http.HttpStatus;

public class AiException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public AiException(ExceptionMessage message) {
        super(message.getMessage());
        this.exceptionMessage = message;
    }

    public HttpStatus getHttpStatus() {
        return exceptionMessage.getHttpStatus();
    }

    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}

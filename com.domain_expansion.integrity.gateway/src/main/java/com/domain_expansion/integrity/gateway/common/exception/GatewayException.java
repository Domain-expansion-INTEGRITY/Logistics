package com.domain_expansion.integrity.gateway.common.exception;

import com.domain_expansion.integrity.gateway.common.message.ExceptionMessage;
import org.springframework.http.HttpStatus;

public class GatewayException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public GatewayException(ExceptionMessage message) {
        super(message.getMessage());
        this.exceptionMessage = message;
    }

    public HttpStatus getHttpStatus() {
        return this.exceptionMessage.getHttpStatus();
    }

    public String getMessage() {
        return this.exceptionMessage.getMessage();
    }

}

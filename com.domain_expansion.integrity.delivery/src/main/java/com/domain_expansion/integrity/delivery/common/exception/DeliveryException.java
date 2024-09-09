package com.domain_expansion.integrity.delivery.common.exception;

import com.domain_expansion.integrity.delivery.common.message.ExceptionMessage;
import org.springframework.http.HttpStatus;

public class DeliveryException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public DeliveryException(ExceptionMessage exceptionMessage) {
        super("[Delivery Exception]: " + exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }

    public HttpStatus getHttpStatus() {

        return exceptionMessage.getHttpStatus();
    }

    public String getMessage() {

        return exceptionMessage.getMessage();
    }
}
package com.domain_expansion.integrity.product.common.exception;

import com.domain_expansion.integrity.product.common.message.ExceptionMessage;
import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public ProductException(ExceptionMessage exceptionMessage) {
        super("[Product Exception]: " + exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }

    public HttpStatus getHttpStatus() {

        return exceptionMessage.getHttpStatus();
    }

    public String getMessage() {

        return exceptionMessage.getMessage();
    }
}
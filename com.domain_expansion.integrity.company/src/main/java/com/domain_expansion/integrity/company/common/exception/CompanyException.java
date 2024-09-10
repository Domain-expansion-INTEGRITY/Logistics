package com.domain_expansion.integrity.company.common.exception;

import com.domain_expansion.integrity.company.common.message.ExceptionMessage;
import org.springframework.http.HttpStatus;

public class CompanyException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public CompanyException(ExceptionMessage exceptionMessage) {
        super("[Company Exception]: " + exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }

    public HttpStatus getHttpStatus() {

        return exceptionMessage.getHttpStatus();
    }

    public String getMessage() {

        return exceptionMessage.getMessage();
    }
}

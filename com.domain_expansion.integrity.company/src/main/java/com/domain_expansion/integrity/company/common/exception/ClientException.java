package com.domain_expansion.integrity.company.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public ClientException(HttpStatus httpStatus, String message) {
        super("[Client Exception]: " + message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
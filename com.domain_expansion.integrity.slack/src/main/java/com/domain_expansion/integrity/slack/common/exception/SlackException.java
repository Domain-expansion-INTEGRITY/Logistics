package com.domain_expansion.integrity.slack.common.exception;

import com.domain_expansion.integrity.slack.common.message.ExceptionMessage;
import org.springframework.http.HttpStatus;

public class SlackException extends RuntimeException {

    private final ExceptionMessage message;

    public SlackException(ExceptionMessage message) {
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

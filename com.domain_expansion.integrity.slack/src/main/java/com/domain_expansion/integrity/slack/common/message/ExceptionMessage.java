package com.domain_expansion.integrity.slack.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {
    // TODO: slack 예외 처리 메시지
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

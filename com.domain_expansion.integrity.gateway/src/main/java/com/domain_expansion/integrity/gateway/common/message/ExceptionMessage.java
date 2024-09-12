package com.domain_expansion.integrity.gateway.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {

    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 인증정보입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

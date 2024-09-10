package com.domain_expansion.integrity.ai.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {

    INVALID_PROMPT_TYPE(HttpStatus.BAD_REQUEST, "잘못된 Prompt 타입 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}

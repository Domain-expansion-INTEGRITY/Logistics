package com.domain_expansion.integrity.ai.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_CREATE_AI(HttpStatus.CREATED, "AI 생성에 성공하였습니다."),
    SUCCESS_FIND_AI(HttpStatus.OK, "AI 조회에 성공하였습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

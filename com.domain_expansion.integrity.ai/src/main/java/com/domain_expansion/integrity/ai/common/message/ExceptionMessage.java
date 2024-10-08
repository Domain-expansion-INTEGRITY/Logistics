package com.domain_expansion.integrity.ai.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {

    INVALID_HISTORY_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 History 아이디입니다."),
    INVALID_PROMPT_TYPE(HttpStatus.BAD_REQUEST, "잘못된 Prompt 타입 입니다."),
    AUTHORIZATION(HttpStatus.UNAUTHORIZED, "잘못된 인증정보 입니다."),

    GEMINI_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini api 호출에서 오류가 발생했습니다."),
    WEATHER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "기상청 api 호출에서 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}

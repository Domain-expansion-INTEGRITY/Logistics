package com.domain_expansion.integrity.slack.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {
    // TODO: slack 예외 처리 메시지
    AUTHORIZATION(HttpStatus.UNAUTHORIZED, "잘못된 인증정보입니다."),
    SLACK_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "슬랙 전송에 실패하였습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    SLACK_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 슬랙 아이디입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}

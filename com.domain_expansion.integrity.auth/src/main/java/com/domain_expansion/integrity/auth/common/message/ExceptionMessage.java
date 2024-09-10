package com.domain_expansion.integrity.auth.common.message;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {
    //TODO: 에러 메시지 작성
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}

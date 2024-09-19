package com.domain_expansion.integrity.user.common.message;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {
    //TODO: 에러 메시지 작성
    //
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력값 입니다."),
    INVALID_PHONE_NUMBER_INPUT(HttpStatus.BAD_REQUEST, "잘못된 휴대전화 번호 입니다."),
    ALREADY_EXIST_DATA(HttpStatus.BAD_REQUEST, "이미 존재하는 필드값 입니다."),
    //
    AUTHORIZATION(HttpStatus.UNAUTHORIZED, "잘못된 인증정보입니다."),
    //
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
    ;


    private final HttpStatus httpStatus;
    private final String message;

}

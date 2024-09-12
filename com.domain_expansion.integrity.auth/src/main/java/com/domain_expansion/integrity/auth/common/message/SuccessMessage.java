package com.domain_expansion.integrity.auth.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_LOGIN_USER(HttpStatus.OK, "로그인에 성공하였습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

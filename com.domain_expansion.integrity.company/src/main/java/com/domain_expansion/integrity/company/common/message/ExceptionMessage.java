package com.domain_expansion.integrity.company.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    NOT_FOUND_HUB_ID(BAD_REQUEST, "관리 허브ID가 존재하지 않습니다."),
    NOT_FOUND_COMPANY_ID(BAD_REQUEST, "존재하지 않는 업체입니다."),
    NOT_AUTHORIZED(UNAUTHORIZED,"접근 권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

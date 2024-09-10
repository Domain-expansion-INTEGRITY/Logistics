package com.domain_expansion.integrity.company.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SUCCESS_CREATE_COMPANY(HttpStatus.CREATED, "업체 등록이 완료 되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

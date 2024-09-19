package com.domain_expansion.integrity.delivery.common.message;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    NOT_FOUND_DELIVERY(NOT_FOUND, "배달을 찾지 못했습니다."),

    CANT_JOIN_BECAUSE_HUB_DELIVERY_MAN(BAD_REQUEST, "업체 배송 담당자는 허브 배송 담당자란에 등록할 수 없습니다."),
    CANT_JOIN_BECAUSE_DELIVERY_MAN(BAD_REQUEST, "허브 배송 담당자는 업체 배송 담당자란에 등록할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

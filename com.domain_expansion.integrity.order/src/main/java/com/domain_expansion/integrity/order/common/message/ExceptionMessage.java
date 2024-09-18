package com.domain_expansion.integrity.order.common.message;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    PRODUCT_SERVER_ERROR(BAD_REQUEST, "상품 서버에서 오류가 발생했습니다. 존재하지 않는 상품이 있을 수 있으니 확인해주세요."),
    NOT_FOUND_ORDER(NOT_FOUND, "주문을 찾지 못했습니다."),
    GUARD(FORBIDDEN, "권한이 없습니다."),
    IS_NOT_BUYER(BAD_REQUEST, "수령 업체가 아닙니다."),
    IS_NOT_SELLER(BAD_REQUEST, "공급 업체가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

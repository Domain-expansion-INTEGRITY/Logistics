package com.domain_expansion.integrity.product.common.message;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    GUARD(UNAUTHORIZED, "권한이 없습니다."),

    NOT_FOUND_PRODUCT(NOT_FOUND, "상품을 찾을 수 없습니다."),
    NOT_FOUND_COMPANY(NOT_FOUND, "업체를 찾을 수 없습니다."),

    STOCK_MUST_HAVE_VALUE(BAD_REQUEST, "재고는 음수가 될 수 없습니다."),
    PRODUCT_NAME_MIN_VALUE(BAD_REQUEST, "상품명은 최소 1글자 이상 입력해야 합니다."),
    PRODUCT_NAME_MAX_VALUE(BAD_REQUEST, "상품명은 최대 255글자 까지 입력할 수 있습니다."),

    ;
    private final HttpStatus httpStatus;
    private final String message;
}

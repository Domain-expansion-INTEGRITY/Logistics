package com.domain_expansion.integrity.delivery.common.message;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    PRODUCT_SERVER_ERROR(BAD_REQUEST, "상품 서버에서 오류가 발생했습니다. 존재하지 않는 상품이 있을 수 있으니 확인해주세요."),

    PRODUCT_NAME_MIN_VALUE(BAD_REQUEST, "상품명은 최소 1글자 이상 입력해야 합니다."),
    PRODUCT_NAME_MAX_VALUE(BAD_REQUEST, "상품명은 최대 255글자 까지 입력할 수 있습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

package com.domain_expansion.integrity.product.common.message;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SUCCESS_CREATE_PRODUCT(CREATED, "상품 등록이 완료 되었습니다."),
    SUCCESS_GET_PRODUCT(OK, "상품 단 건 조회가 완료 되었습니다."),
    SUCCESS_GET_PRODUCTS(OK, "상품 목록 조회가 완료 되었습니다."),
    SUCCESS_UPDATE_PRODUCT(OK, "상품이 수정이 완료 되었습니다."),
    SUCCESS_DELETE_PRODUCT(OK, "성공적으로 상품이 삭제되었습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}

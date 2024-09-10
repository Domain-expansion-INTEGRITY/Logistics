package com.domain_expansion.integrity.order.common.message;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SUCCESS_CREATE_ORDER(CREATED, "주문 등록이 완료 되었습니다."),
    SUCCESS_GET_ORDER(OK, "주문 단 건 조회가 완료 되었습니다."),
    SUCCESS_GET_ORDERS(OK, "주문 목록 조회가 완료 되었습니다."),
    SUCCESS_UPDATE_ORDER(OK, "주문이 수정이 완료 되었습니다."),
    SUCCESS_DELETE_ORDER(OK, "성공적으로 주문이 삭제되었습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}

package com.domain_expansion.integrity.delivery.common.message;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SUCCESS_CREATE_DELIVERY(CREATED, "배달 등록이 완료 되었습니다."),
    SUCCESS_GET_DELIVERY(OK, "배달 단 건 조회가 완료 되었습니다."),
    SUCCESS_GET_DELIVERIES(OK, "배달 목록 조회가 완료 되었습니다."),
    SUCCESS_UPDATE_DELIVERY(OK, "배달 수정이 완료 되었습니다."),
    SUCCESS_UPDATE_DELIVERY_DELIVERY_MAN(OK, "허브 배송 담당자 수정이 완료 되었습니다."),
    SUCCESS_UPDATE_DELIVERY_HUB_DELIVERY_MAN(OK, "업체 배송 담당자 수정이 완료 되었습니다."),
    SUCCESS_DELETE_DELIVERY(OK, "성공적으로 배달이 삭제되었습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}

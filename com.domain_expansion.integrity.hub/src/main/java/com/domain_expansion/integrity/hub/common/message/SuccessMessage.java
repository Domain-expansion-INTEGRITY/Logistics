package com.domain_expansion.integrity.hub.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SUCCESS_CREATE_HUB(HttpStatus.CREATED, "허브 등록이 완료 되었습니다."),
    SUCCESS_GET_HUB(HttpStatus.OK, "허브 단건 조회가 완료 되었습니다."),
    SUCCESS_GET_ALL_HUBS(HttpStatus.OK, "허브 목록 조회가 완료 되었습니다."),
    SUCCESS_UPDATE_HUB(HttpStatus.OK, "허브 수정이 완료 되었습니다."),
    SUCCESS_DELETE_HUBS(HttpStatus.OK, "허브 삭제가 완료 되었습니다."),
    SUCCESS_CREATE_HUB_ROUTE(HttpStatus.OK, "허브 이동정보 등록이 완료 되었습니다."),
    SUCCESS_GET_HUB_ROUTE(HttpStatus.OK, "허브 이동정보 단건 조회가 완료 되었습니다."),
    SUCCESS_GET_ALL_HUBS_ROUTE(HttpStatus.OK, "허브 이동정보 목록 조회가 완료 되었습니다."),
    SUCCESS_UPDATE_HUB_ROUTE(HttpStatus.OK, "허브 이동정보 수정이 완료 되었습니다."),
    SUCCESS_DELETE_HUBS_ROUTE(HttpStatus.OK, "허브 이동정보 삭제가 완료 되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

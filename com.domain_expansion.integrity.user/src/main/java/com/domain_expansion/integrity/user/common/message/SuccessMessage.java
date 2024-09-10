package com.domain_expansion.integrity.user.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_CREATE_USER(HttpStatus.CREATED, "유저 생성이 완료되었습니다."),
    SUCCESS_FIND_SINGLE_USER(HttpStatus.OK, "유저 단일 조회가 완료되었습니다."),
    SUCCESS_FIND_USER_LIST(HttpStatus.OK, "유저 목록 조회가 완료되었습니다."),
    SUCCESS_UPDATE_USER(HttpStatus.OK, "유저 업데이트가 완료되었습니다."),
    SUCCESS_DELETE_USER(HttpStatus.OK, "유저 삭제가 완료되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

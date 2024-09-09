package com.domain_expansion.integrity.slack.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {
    //TODO: 슬랙 성공 메세지
    SUCCESS_CREATE_SLACK(HttpStatus.CREATED, "슬랙 생성에 성공하였습니다."),
    SUCCESS_FIND_SLACKLIST(HttpStatus.CREATED, "슬랙 목록 조회에 성공하였습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

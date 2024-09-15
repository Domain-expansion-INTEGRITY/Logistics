package com.domain_expansion.integrity.hub.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    LONGITUDE_MUST_BETWEEN_180_MINUS_180(HttpStatus.BAD_REQUEST, "경도는 -180과 180의 사이로 설정해야됩니다."),
    LATITUDE_MUST_BETWEEN_90_MINUS_90(HttpStatus.BAD_REQUEST, "위도는 -90과 90의 사이로 설정해야됩니다."),
    STOCK_MUST_NOT_MINUS(HttpStatus.BAD_REQUEST, "재고는 음수로 신청할 수 없습니다."),
    NOT_FOUND_HUB_ID(HttpStatus.BAD_REQUEST,"존재하지 않는 허브 ID입니다."),
    NOT_FOUND_HUB_ROUTE_ID(HttpStatus.BAD_REQUEST,"존재하지 않는 허브 이동 정보 ID입니다."),
    NOT_FOUND_ROUTE(HttpStatus.NOT_FOUND,"이동할 수 있는 경로가 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

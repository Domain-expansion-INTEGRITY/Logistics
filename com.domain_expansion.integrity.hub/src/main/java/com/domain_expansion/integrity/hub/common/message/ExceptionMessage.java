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
    NOT_FOUND_HUB_ID(HttpStatus.NOT_FOUND,"존재하지 않는 허브 ID입니다."),
    NOT_FOUND_HUB_ROUTE_ID(HttpStatus.NOT_FOUND,"존재하지 않는 허브 이동 정보 ID입니다."),
    NOT_FOUND_ROUTE(HttpStatus.NOT_FOUND,"이동할 수 있는 경로가 없습니다."),
    ROUTE_MUST_NOT_SAME(HttpStatus.BAD_REQUEST,"출발지와 목적지가 같습니다."),
    ALREADY_EXISTED_USER(HttpStatus.BAD_REQUEST,"이미 등록된 배송담당자입니다."),
    NOT_FOUND_DELIVERY_MAN(HttpStatus.NOT_FOUND,"존재하지 않는 배송담당자 아이디입니다."),
    DONT_HAVE_AUTHORIZED(HttpStatus.UNAUTHORIZED,"자신의 허브에 소속된 배송담당자만 관리할 수 있습니다."),
    RE_CHECK_DELIVERY_MAN_ID(HttpStatus.BAD_REQUEST,"다시 한번 배송담당자들을 확인해주세요"),
    DELIVERY_MAN_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"이미 업체 배송담당자로 등록되어있습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}

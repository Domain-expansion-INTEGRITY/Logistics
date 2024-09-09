package com.domain_expansion.integrity.hub.common.message;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    LONGITUDE_MUST_BETWEEN_180_MINUS_180(HttpStatus.BAD_REQUEST, "경도는 -180과 180의 사이로 설정해야됩니다."),
    Latitude_MUST_BETWEEN_90_MINUS_90(HttpStatus.BAD_REQUEST, "위도는 -90과 90의 사이로 설정해야됩니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

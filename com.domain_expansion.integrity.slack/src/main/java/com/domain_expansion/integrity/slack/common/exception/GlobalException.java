package com.domain_expansion.integrity.slack.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.domain_expansion.integrity.slack.common.response.CommonResponse;
import com.domain_expansion.integrity.slack.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    // 전체 에러 핸들링
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<? extends CommonResponse> handleException(Exception e) {

        //TODO: 로깅 범위 조장

        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(e.getMessage()));
    }

}

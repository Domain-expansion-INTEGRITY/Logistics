package com.domain_expansion.integrity.ai.common.exception;

import com.domain_expansion.integrity.ai.common.response.CommonResponse;
import com.domain_expansion.integrity.ai.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = AiException.class)
    public ResponseEntity<? extends CommonResponse> handleAiException(AiException e) {

        return ResponseEntity.status(e.getHttpStatus())
            .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<? extends CommonResponse> handleException(Exception e) {

        // TODO: 로깅 범위 조정

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(e.getMessage()));
    }
}

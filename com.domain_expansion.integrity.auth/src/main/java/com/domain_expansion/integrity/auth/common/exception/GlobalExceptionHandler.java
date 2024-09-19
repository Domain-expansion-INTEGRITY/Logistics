package com.domain_expansion.integrity.auth.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.domain_expansion.integrity.auth.common.response.CommonResponse;
import com.domain_expansion.integrity.auth.common.response.ErrorResponse;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<? extends CommonResponse> handleProductException(AuthException e) {

        //TODO: 로깅 범위 조장

        return ResponseEntity.status(e.getHttpStatus())
            .body(ErrorResponse.of(e.getMessage()));
    }

    // Feign에서 오는 Error Handling
    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<? extends CommonResponse> feignExceptionHandler(FeignException e) {

        //TODO: 로깅 범위 조장

        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(e.getMessage()));
    }

    // 전체 에러 핸들링
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<? extends CommonResponse> handleException(Exception e) {

        //TODO: 로깅 범위 조장
        e.printStackTrace();

        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(e.getMessage()));
    }
}

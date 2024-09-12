package com.domain_expansion.integrity.product.common.exception;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.domain_expansion.integrity.product.common.response.CommonResponse;
import com.domain_expansion.integrity.product.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ProductException.class)
    public ResponseEntity<? extends CommonResponse> handleProductException(ProductException e) {

        log.error("An exception occurred", e);

        return ResponseEntity.status(e.getHttpStatus()).body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<? extends CommonResponse> handleValidException(MethodArgumentNotValidException e) {

        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.error("An exception occurred", e);

        return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.of(defaultMessage));
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<? extends CommonResponse> handleUnsupportedOperationException(UnsupportedOperationException e) {

        log.error("An exception occurred", e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponse.of("Server Error"));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<? extends CommonResponse> handleException(Exception e) {

        log.error("An exception occurred", e);

        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(e.getMessage()));
    }
}

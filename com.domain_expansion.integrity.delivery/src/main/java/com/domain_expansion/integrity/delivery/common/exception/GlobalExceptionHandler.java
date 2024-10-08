package com.domain_expansion.integrity.delivery.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.domain_expansion.integrity.delivery.common.response.CommonResponse;
import com.domain_expansion.integrity.delivery.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DeliveryException.class)
    public ResponseEntity<? extends CommonResponse> handleProductException(DeliveryException e) {

        e.printStackTrace();

        return ResponseEntity.status(e.getHttpStatus()).body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<? extends CommonResponse> handleValidException(MethodArgumentNotValidException e) {

        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        e.printStackTrace();

        return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.of(defaultMessage));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<? extends CommonResponse> handleException(Exception e) {

        e.printStackTrace();

        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(e.getMessage()));
    }
}

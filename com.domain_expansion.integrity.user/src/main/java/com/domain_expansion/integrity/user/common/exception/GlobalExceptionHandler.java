package com.domain_expansion.integrity.user.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.domain_expansion.integrity.user.common.response.CommonResponse;
import com.domain_expansion.integrity.user.common.response.ErrorResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<? extends CommonResponse> handleProductException(UserException e) {

        //TODO: 로깅 범위 조장

        return ResponseEntity.status(e.getHttpStatus())
            .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<? extends CommonResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {

        List<InvalidMethodResponseDto> invalidInputResList = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(ex -> new InvalidMethodResponseDto(ex.getField(),
                ex.getDefaultMessage())) // defaultMessage 필드명을 message 변경
            .toList();

        return ResponseEntity.status(BAD_REQUEST)
            .body(ErrorResponse.of(invalidInputResList.toString()));
    }


    // 전체 에러 핸들링
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<? extends CommonResponse> handleException(Exception e) {

        //TODO: 로깅 범위 조장
        e.printStackTrace();

        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(e.getMessage()));
    }
}

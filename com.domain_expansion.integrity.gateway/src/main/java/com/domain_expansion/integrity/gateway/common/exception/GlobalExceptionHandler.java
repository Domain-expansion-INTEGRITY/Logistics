package com.domain_expansion.integrity.gateway.common.exception;

import com.domain_expansion.integrity.gateway.common.message.ExceptionMessage;
import com.domain_expansion.integrity.gateway.common.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    public Mono<Void> handleErrorResponse(ServerWebExchange exchange,
        ExceptionMessage exceptionMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(exceptionMessage.getHttpStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 커스텀 응답 객체
        ErrorResponse errorResponse = new ErrorResponse(false, exceptionMessage.getMessage());

        // JSON 변환
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(errorResponse);
        } catch (Exception e) {
            // 변환 중 오류 발생 시 빈 응답 반환
            return response.setComplete();
        }

        // 응답에 JSON 데이터를 기록
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }
}

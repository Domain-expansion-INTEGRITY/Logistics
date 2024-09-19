package com.domain_expansion.integrity.ai.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SuccessResponse<T>(

    boolean success,

    String message,

    @JsonInclude(value = Include.NON_NULL)
    T data

) implements CommonResponse {

    public static <T> SuccessResponse<T> of(String message, T data) {

        return SuccessResponse
            .<T>builder()
            .success(true)
            .message(message)
            .data(data)
            .build();

    }

}

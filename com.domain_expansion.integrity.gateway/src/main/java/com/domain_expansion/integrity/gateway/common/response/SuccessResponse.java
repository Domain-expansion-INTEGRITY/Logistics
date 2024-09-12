package com.domain_expansion.integrity.gateway.common.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(access = PRIVATE)
public record SuccessResponse<T>(
    boolean success,

    @NotNull
    String message,

    @JsonInclude(value = NON_NULL)
    T data
) implements CommonResponse {

    public static <T> SuccessResponse<T> of(String message, T data) {
        return SuccessResponse.<T>builder()
            .success(true)
            .message(message)
            .data(data)
            .build();
    }


}

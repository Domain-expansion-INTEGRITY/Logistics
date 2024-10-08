package com.domain_expansion.integrity.company.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SuccessResponse<T> (
        boolean success,
        @NonNull
        String message,
        @JsonInclude(value = NON_NULL) T data
) implements CommonResponse {

    public static <T> SuccessResponse<T> of(String message, T data) {

        return SuccessResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static SuccessResponse<?> of(String message) {

        return SuccessResponse.builder()
                .success(true)
                .message(message)
                .build();
    }
}

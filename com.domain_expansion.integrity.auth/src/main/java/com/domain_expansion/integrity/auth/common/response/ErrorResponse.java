package com.domain_expansion.integrity.auth.common.response;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(access = PRIVATE)
public record ErrorResponse(
    boolean success,
    @NotNull
    String message) implements CommonResponse {

    public static ErrorResponse of(String message) {

        return ErrorResponse.builder()
            .success(false)
            .message(message)
            .build();
    }

}

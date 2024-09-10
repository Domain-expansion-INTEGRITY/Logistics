package com.domain_expansion.integrity.ai.common.response;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ErrorResponse(

    boolean success,
    @NotNull
    String message
) implements CommonResponse {

    public static ErrorResponse of(String message) {

        return ErrorResponse.builder()
            .success(false)
            .message(message)
            .build();
    }

}

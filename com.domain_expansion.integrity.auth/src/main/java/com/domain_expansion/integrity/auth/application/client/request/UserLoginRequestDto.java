package com.domain_expansion.integrity.auth.application.client.request;

import lombok.AccessLevel;
import lombok.Builder;

public record UserLoginRequestDto(
    String username,
    String password
) {

    @Builder(access = AccessLevel.PROTECTED)
    public static UserLoginRequestDto from(String username, String password) {
        return UserLoginRequestDto.builder()
            .username(username)
            .password(password)
            .build();
    }
}

package com.domain_expansion.integrity.auth.application.client.request;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PROTECTED)
public record UserLoginRequestDto(
    String username,
    String password
) {

    public static UserLoginRequestDto from(String username, String password) {
        return UserLoginRequestDto.builder()
            .username(username)
            .password(password)
            .build();
    }
}

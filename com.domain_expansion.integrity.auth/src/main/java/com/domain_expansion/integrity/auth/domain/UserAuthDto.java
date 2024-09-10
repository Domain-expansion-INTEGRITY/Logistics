package com.domain_expansion.integrity.auth.domain;

import com.domain_expansion.integrity.auth.application.client.response.UserResponseDto;

public record UserAuthDto(
    String username,
    UserRole roles
) {

    public static UserAuthDto from(UserResponseDto responseDto) {
        return new UserAuthDto(
            responseDto.username(),
            responseDto.role()
        );
    }
}

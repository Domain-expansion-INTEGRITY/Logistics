package com.domain_expansion.integrity.auth.domain.dto;

import com.domain_expansion.integrity.auth.application.client.response.UserResponseDto;
import com.domain_expansion.integrity.auth.domain.UserRole;

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

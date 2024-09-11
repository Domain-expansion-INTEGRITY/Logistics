package com.domain_expansion.integrity.auth.domain.dto;

import com.domain_expansion.integrity.auth.application.client.response.UserResponseDto;

public record UserAuthDto(
    Long userId,
    String username,
    String role
) {

    public static String getRedisKey(Long userId) {
        return "auth-" + userId;
    }

    public static UserAuthDto from(UserResponseDto responseDto) {
        return new UserAuthDto(
            responseDto.userId(),
            responseDto.username(),
            responseDto.role().getAuthority()
        );
    }
}

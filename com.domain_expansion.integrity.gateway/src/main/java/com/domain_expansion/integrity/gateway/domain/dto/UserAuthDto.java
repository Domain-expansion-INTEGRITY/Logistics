package com.domain_expansion.integrity.gateway.domain.dto;

public record UserAuthDto(
    Long userId,
    String username,
    String role
) {

    public static String getRedisKey(Long userId) {
        return "auth-" + userId;
    }
}

package com.domain_expansion.integrity.auth.application.client.response;

import com.domain_expansion.integrity.auth.domain.UserRole;

public record UserResponseDto(
    Long userId,
    String username,
    UserRole role,
    String phoneNumber,
    String slackId
) {

}

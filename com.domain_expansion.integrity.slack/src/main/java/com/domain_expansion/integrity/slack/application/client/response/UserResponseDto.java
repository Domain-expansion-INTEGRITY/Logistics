package com.domain_expansion.integrity.slack.application.client.response;

import com.domain_expansion.integrity.slack.application.client.UserRole;

public record UserResponseDto(
    Long userId,
    String username,
    UserRole role,
    String phoneNumber,
    String slackId
) {

}

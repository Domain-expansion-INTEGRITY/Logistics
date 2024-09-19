package com.domain_expansion.integrity.user.presentation.request;

import com.domain_expansion.integrity.user.domain.model.UserRole;

public record UserUpdateRequestDto(
    UserRole role,
    String slackId,
    String phoneNumber
) {

}

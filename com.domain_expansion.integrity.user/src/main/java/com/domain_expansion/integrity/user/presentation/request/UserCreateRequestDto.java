package com.domain_expansion.integrity.user.presentation.request;

import com.domain_expansion.integrity.user.domain.model.UserRole;

public record UserCreateRequestDto(
    String username,
    String password,
    UserRole role,
    String phoneNumber,
    String slackId

) {

}

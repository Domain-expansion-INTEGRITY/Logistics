package com.domain_expansion.integrity.user.presentation.request;

import com.domain_expansion.integrity.user.domain.model.UserRole;

public record UserSearchCondition(
    UserRole role
) {

}

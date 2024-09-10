package com.domain_expansion.integrity.user.presentation.request;

public record UserLoginRequestDto(
    String username,
    String password
) {

}

package com.domain_expansion.integrity.auth.presentation.request;

public record AuthLoginRequestDto(
    String username,
    String password
) {

}

package com.domain_expansion.integrity.auth.application.client.request;

public record UserLoginRequestDto(
    String username,
    String password
) {

}

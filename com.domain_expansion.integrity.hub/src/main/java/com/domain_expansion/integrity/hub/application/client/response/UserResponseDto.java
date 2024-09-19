package com.domain_expansion.integrity.hub.application.client.response;

public record UserResponseDto(
        String username,
        String role,
        String phoneNumber,
        String slackId
){

}

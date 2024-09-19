package com.domain_expansion.integrity.slack.presentation.request;

public record SlackCreateRequestDto(
    Long receivedId, // 받는 유저 아이디
    String message
) {

}

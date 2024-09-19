package com.domain_expansion.integrity.delivery.presentation.request;

public record SlackCreateRequestDto(
        String receiveId,
        String message
) {

}

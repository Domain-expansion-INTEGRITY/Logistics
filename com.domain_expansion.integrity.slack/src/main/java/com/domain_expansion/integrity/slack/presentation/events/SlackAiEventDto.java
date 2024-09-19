package com.domain_expansion.integrity.slack.presentation.events;

public record SlackAiEventDto(
    Long userId,
    String aiAnswer
) {

}

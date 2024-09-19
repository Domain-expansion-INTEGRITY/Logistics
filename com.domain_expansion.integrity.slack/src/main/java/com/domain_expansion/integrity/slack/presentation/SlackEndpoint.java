package com.domain_expansion.integrity.slack.presentation;

import com.domain_expansion.integrity.slack.application.SlackService;
import com.domain_expansion.integrity.slack.common.EventSerializer;
import com.domain_expansion.integrity.slack.presentation.events.SlackAiEventDto;
import com.domain_expansion.integrity.slack.presentation.request.SlackCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackEndpoint {

    private final SlackService slackService;

    @KafkaListener(groupId = "ai-slack-group", topics = "ai.slack")
    public void aiMessageToSlack(String message) {
        SlackAiEventDto output = EventSerializer.deserialize(message, SlackAiEventDto.class);
        slackService.createSlackMessage(
            new SlackCreateRequestDto(output.userId(), output.aiAnswer()));

    }

}

package com.domain_expansion.integrity.slack.presentation.response;

import com.domain_expansion.integrity.slack.domain.model.Slack;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SlackResponseDto(
    String slackId,
    Long receivedId,
    String message,
    LocalDateTime sendTime
) {

    public static SlackResponseDto from(Slack slack) {
        return SlackResponseDto.builder()
            .slackId(slack.getId())
            .receivedId(slack.getReceiveId())
            .message(slack.getMessage())
            .sendTime(slack.getSendTime())
            .build();
    }

}

package com.domain_expansion.integrity.slack.domain.mapper;

import com.domain_expansion.integrity.slack.domain.model.Slack;
import com.domain_expansion.integrity.slack.presentation.request.SlackCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class SlackMapper {

    public Slack createDtoToSlack(SlackCreateRequestDto requestDto, String slackId) {

        return Slack.from(
            slackId, requestDto.receivedId(), requestDto.message()
        );

    }

}

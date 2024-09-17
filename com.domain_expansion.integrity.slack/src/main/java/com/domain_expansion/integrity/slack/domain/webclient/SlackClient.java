package com.domain_expansion.integrity.slack.domain.webclient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface SlackClient {

    @PostExchange("/{slackId}")
    String sendSlackMessage(
        @PathVariable("slackId") String slackId,
        @RequestBody SlackRequestDto requestDto
    );

}

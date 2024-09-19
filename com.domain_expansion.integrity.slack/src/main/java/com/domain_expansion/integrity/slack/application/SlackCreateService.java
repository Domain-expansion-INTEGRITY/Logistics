package com.domain_expansion.integrity.slack.application;

import com.domain_expansion.integrity.slack.presentation.request.SlackCreateRequestDto;
import com.domain_expansion.integrity.slack.presentation.response.SlackResponseDto;

public interface SlackCreateService {

    SlackResponseDto createSlackMessage(SlackCreateRequestDto requestDto);

}

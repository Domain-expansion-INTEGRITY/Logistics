package com.domain_expansion.integrity.slack.application;

import com.domain_expansion.integrity.slack.presentation.response.SlackResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackReadService {


    SlackResponseDto findSlackById(String slackId);

    Page<SlackResponseDto> findSlackList(Pageable pageable);

}

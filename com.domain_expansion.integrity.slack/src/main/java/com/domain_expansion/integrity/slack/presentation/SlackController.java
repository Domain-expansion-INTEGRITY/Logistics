package com.domain_expansion.integrity.slack.presentation;

import com.domain_expansion.integrity.slack.application.SlackService;
import com.domain_expansion.integrity.slack.common.message.SuccessMessage;
import com.domain_expansion.integrity.slack.common.response.SuccessResponse;
import com.domain_expansion.integrity.slack.presentation.request.SlackCreateRequestDto;
import com.domain_expansion.integrity.slack.presentation.response.SlackResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/slacks")
@RestController
public class SlackController {

    private final SlackService slackService;

    /**
     * 슬랙 메세지 생성
     */
    @PostMapping
    public SuccessResponse<SlackResponseDto> createSlackMessage(
        @RequestBody SlackCreateRequestDto requestDto
    ) {

        SlackResponseDto slackInfo = slackService.createSlackMessage(requestDto);
        return SuccessResponse.of(SuccessMessage.SUCCESS_CREATE_SLACK.getMessage(), slackInfo);
    }

    /**
     * slack 메세지 전체 조회
     */
    @GetMapping
    public SuccessResponse<?> findSlackMessageList(
        @PageableDefault(value = 10, size = 10, page = 0) Pageable pageable
    ) {
        //TODO: 관리자 전용
        Page<SlackResponseDto> slackList = slackService.findSlackList(pageable);

        return SuccessResponse.of(SuccessMessage.SUCCESS_FIND_SLACKLIST.getMessage(),
            slackList
        );
    }

    /**
     * slack 메세지 개별 조회
     */
    @GetMapping("{id}")
    public SuccessResponse<?> findSlackMessageById() {

        return SuccessResponse.of("", null);
    }


}

package com.domain_expansion.integrity.slack.presentation;

import static com.domain_expansion.integrity.slack.common.message.SuccessMessage.SUCCESS_CREATE_SLACK;
import static com.domain_expansion.integrity.slack.common.message.SuccessMessage.SUCCESS_FIND_SLACK;
import static com.domain_expansion.integrity.slack.common.message.SuccessMessage.SUCCESS_FIND_SLACKLIST;

import com.domain_expansion.integrity.slack.application.SlackService;
import com.domain_expansion.integrity.slack.common.aop.DefaultPageSize;
import com.domain_expansion.integrity.slack.common.response.CommonResponse;
import com.domain_expansion.integrity.slack.common.response.SuccessResponse;
import com.domain_expansion.integrity.slack.presentation.request.SlackCreateRequestDto;
import com.domain_expansion.integrity.slack.presentation.response.SlackResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<? extends CommonResponse> createSlackMessage(
        @RequestBody SlackCreateRequestDto requestDto
    ) {

        SlackResponseDto slackInfo = slackService.createSlackMessage(requestDto);
        return ResponseEntity.status(SUCCESS_CREATE_SLACK.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_CREATE_SLACK.getMessage(), slackInfo));
    }

    /**
     * slack 메세지 전체 조회
     */
    @PreAuthorize("hasAnyRole('ROLE_MASTER')")
    @DefaultPageSize
    @GetMapping
    public ResponseEntity<? extends CommonResponse> findSlackMessageList(
        @PageableDefault(value = 10, size = 10, page = 0) Pageable pageable
    ) {

        Page<SlackResponseDto> slackList = slackService.findSlackList(pageable);

        return ResponseEntity.status(SUCCESS_FIND_SLACKLIST.getHttpStatus()).body(
            SuccessResponse.of(SUCCESS_FIND_SLACKLIST.getMessage(),
                slackList
            )
        );
    }

    /**
     * slack 메세지 개별 조회
     */
    @PreAuthorize("hasAnyRole('ROLE_MASTER')")
    @GetMapping("/{id}")
    public ResponseEntity<? extends CommonResponse> findSlackMessageById(
        @PathVariable("id") String id
    ) {

        SlackResponseDto slackById = slackService.findSlackById(id);

        return ResponseEntity.status(SUCCESS_FIND_SLACK.getHttpStatus()).body(
            SuccessResponse.of(SUCCESS_FIND_SLACK.getMessage(),
                slackById
            )
        );
    }


}

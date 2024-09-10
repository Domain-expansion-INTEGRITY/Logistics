package com.domain_expansion.integrity.ai.presentation;

import com.domain_expansion.integrity.ai.application.aiHistory.AiService;
import com.domain_expansion.integrity.ai.common.message.SuccessMessage;
import com.domain_expansion.integrity.ai.common.response.SuccessResponse;
import com.domain_expansion.integrity.ai.presentation.request.AiCreateRequestDto;
import com.domain_expansion.integrity.ai.presentation.response.AiResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/ais")
@RestController
public class AiController {

    private final AiService aiService;

    /**
     * ai 응답 생성
     */
    @PostMapping
    public SuccessResponse<?> createAi(
        @RequestBody AiCreateRequestDto requestDto
    ) {

        AiResponseDto ai = aiService.createAi(requestDto);
        return SuccessResponse.of(SuccessMessage.SUCCESS_CREATE_AI.getMessage(), ai);
    }


    /**
     * ai 목록 조회
     */
    @GetMapping
    public SuccessResponse<List<AiResponseDto>> findAiInfoList() {
        // TODO: 관리자 전용

        return SuccessResponse.of("", null);
    }

    /**
     * ai 단일 조회
     */
    @GetMapping("/{aiId}")
    public SuccessResponse<AiResponseDto> findAiInfoById(
        @PathVariable("aiId") String id
    ) {

        return SuccessResponse.of("", null);
    }

}

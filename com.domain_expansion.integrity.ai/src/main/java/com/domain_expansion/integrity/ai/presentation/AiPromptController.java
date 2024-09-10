package com.domain_expansion.integrity.ai.presentation;

import com.domain_expansion.integrity.ai.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/ais")
@RestController
public class AiPromptController {

    /**
     * ai prompt 생성
     */
    @PostMapping("/proms")
    public SuccessResponse<?> createPrompt() {

        return SuccessResponse.of("", null);
    }

    /**
     * ai prompt 목록 조회
     */
    @GetMapping("/proms")
    public SuccessResponse<?> findPromptList() {

        return SuccessResponse.of("", null);
    }

}

package com.domain_expansion.integrity.ai.presentation;

import static com.domain_expansion.integrity.ai.common.message.SuccessMessage.SUCCESS_FIND_AI;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.domain_expansion.integrity.ai.application.aiHistory.AiService;
import com.domain_expansion.integrity.ai.common.aop.DefaultPageSize;
import com.domain_expansion.integrity.ai.common.message.SuccessMessage;
import com.domain_expansion.integrity.ai.common.response.CommonResponse;
import com.domain_expansion.integrity.ai.common.response.SuccessResponse;
import com.domain_expansion.integrity.ai.domain.model.PromptType;
import com.domain_expansion.integrity.ai.presentation.request.AiCompanyDeliverRequestDto;
import com.domain_expansion.integrity.ai.presentation.request.AiDeliverySequenceRequestDto;
import com.domain_expansion.integrity.ai.presentation.request.AiHubDeliveryRequestDto;
import com.domain_expansion.integrity.ai.presentation.response.AiDeliverySlackResponseDto;
import com.domain_expansion.integrity.ai.presentation.response.AiResponseDto;
import com.domain_expansion.integrity.ai.presentation.response.CompanyDeliveryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/ais")
@RestController
public class AiController {

    private final AiService aiService;

    /**
     * 업체 배송 담당자들에게 전송할 슬랙 메시지를 생성
     */
    @PostMapping("/company-delivery/slack")
    public ResponseEntity<? extends CommonResponse> createSlackForCompanyDelivery(
        @Validated @RequestBody AiCompanyDeliverRequestDto requestDto
    ) {

        AiDeliverySlackResponseDto ai = aiService.createCompanyDeliverySlackMessage(requestDto);

        return ResponseEntity.status(SuccessMessage.SUCCESS_CREATE_AI.getHttpStatus())
            .body(SuccessResponse.of(SuccessMessage.SUCCESS_CREATE_AI.getMessage(), ai));
    }

    /**
     * 허브 배송담당자를 위한 슬랙메세지
     */
    @PostMapping("/hub-delivery/slack")
    public ResponseEntity<? extends CommonResponse> createSlackForHubDelivery(
        @Validated @RequestBody AiHubDeliveryRequestDto requestDto
    ) {

        AiDeliverySlackResponseDto hubDeliverySlackMessage = aiService.createHubDeliverySlackMessage(
            requestDto);
        
        return ResponseEntity.status(SuccessMessage.SUCCESS_CREATE_AI.getHttpStatus())
            .body(SuccessResponse.of(SuccessMessage.SUCCESS_CREATE_AI.getMessage(),
                hubDeliverySlackMessage));
    }


    /**
     * 배송 경로들을 받아서 최적의 경로를 계산해줌
     */
    @PostMapping("/company-delivery/sequence")
    public ResponseEntity<? extends CommonResponse> findBestPath(
        @Validated @RequestBody AiDeliverySequenceRequestDto requestDto
    ) {

        CompanyDeliveryResponseDto companyDeliveryPath = aiService.createCompanyDeliveryPath(
            requestDto);

        return ResponseEntity.status(SuccessMessage.SUCCESS_CREATE_AI.getHttpStatus())
            .body(SuccessResponse.of(SuccessMessage.SUCCESS_CREATE_AI.getMessage(),
                companyDeliveryPath));
    }

    /**
     * ai 목록 조회
     */
    @DefaultPageSize
    @PreAuthorize("hasAnyRole('ROLE_MASTER')")
    @GetMapping
    public ResponseEntity<? extends CommonResponse> findAiInfoList(
        @RequestParam(value = "type", required = false) PromptType promptType,
        @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable
    ) {

        Page<AiResponseDto> aiHistoryList = aiService.findAiHistoryList(pageable, promptType);

        return ResponseEntity.status(SUCCESS_FIND_AI.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_FIND_AI.getMessage(), aiHistoryList));
    }

    /**
     * ai 단일 조회
     */
    @PreAuthorize("hasAnyRole('ROLE_MASTER')")
    @GetMapping("/{aiId}")
    public ResponseEntity<? extends CommonResponse> findAiInfoById(
        @PathVariable("aiId") String id
    ) {
        AiResponseDto aiHistoryById = aiService.findAiHistoryById(id);
        return ResponseEntity.status(SUCCESS_FIND_AI.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_FIND_AI.getMessage(), aiHistoryById));
    }

}

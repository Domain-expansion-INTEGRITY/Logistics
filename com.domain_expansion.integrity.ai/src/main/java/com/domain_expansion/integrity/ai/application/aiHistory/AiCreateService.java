package com.domain_expansion.integrity.ai.application.aiHistory;

import com.domain_expansion.integrity.ai.presentation.request.AiCompanyDeliverRequestDto;
import com.domain_expansion.integrity.ai.presentation.request.AiDeliverySequenceRequestDto;
import com.domain_expansion.integrity.ai.presentation.request.AiHubDeliveryRequestDto;
import com.domain_expansion.integrity.ai.presentation.response.AiDeliverySlackResponseDto;
import com.domain_expansion.integrity.ai.presentation.response.CompanyDeliveryResponseDto;

public interface AiCreateService {

    /**
     * 업체 배송 담당자 슬랙 메세지
     */
    public AiDeliverySlackResponseDto createCompanyDeliverySlackMessage(
        AiCompanyDeliverRequestDto requestDto);

    /**
     * 허브 배송 담당자 슬랙 메세지
     */
    public AiDeliverySlackResponseDto createHubDeliverySlackMessage(
        AiHubDeliveryRequestDto requestDto
    );

    /**
     * 업체 배송 최단 경로
     */
    public CompanyDeliveryResponseDto createCompanyDeliveryPath(
        AiDeliverySequenceRequestDto requestDto);

}

package com.domain_expansion.integrity.ai.presentation.response;

import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PROTECTED)
public record CompanyDeliveryResponseDto(
    String aiId,
    String question,
    List<CompanyDeliveryInfo> answer,
    Long userId
) {

    public static CompanyDeliveryResponseDto from(AiHistory ai,
        List<CompanyDeliveryInfo> deliveryInfoList,
        Long userId) {
        return CompanyDeliveryResponseDto.builder()
            .aiId(ai.getId())
            .userId(userId)
            .question(ai.getQuestion())
            .answer(deliveryInfoList)
            .build();
    }

}

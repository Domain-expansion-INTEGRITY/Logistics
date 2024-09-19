package com.domain_expansion.integrity.ai.presentation.response;

import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PROTECTED)
public record AiDeliverySlackResponseDto(
    String aiId,
    String question,
    String answer,
    Long userId

) {

    public static AiDeliverySlackResponseDto from(AiHistory ai, String answer,
        Long userId) {
        return AiDeliverySlackResponseDto.builder()
            .aiId(ai.getId())
            .userId(userId)
            .question(ai.getQuestion())
            .answer(answer)
            .build();
    }

}

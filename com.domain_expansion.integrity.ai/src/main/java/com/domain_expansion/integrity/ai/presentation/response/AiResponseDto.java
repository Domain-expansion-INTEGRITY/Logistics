package com.domain_expansion.integrity.ai.presentation.response;

import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PROTECTED)
public record AiResponseDto(
    String question,
    String answer,
    Long userId
) {

    public static AiResponseDto from(AiHistory ai, Long userId) {
        return AiResponseDto.builder()
            .userId(userId)
            .question(ai.getQuestion())
            .answer(ai.getAnswer())
            .build();
    }

}

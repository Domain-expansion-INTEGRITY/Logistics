package com.domain_expansion.integrity.ai.presentation.response;

import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AiResponseDto(
    String aiId,
    String question,
    String answer,
    LocalDateTime createdAt
) {

    public static AiResponseDto from(AiHistory aiHistory) {
        return AiResponseDto.builder()
            .aiId(aiHistory.getId())
            .question(aiHistory.getQuestion())
            .answer(aiHistory.getAnswer())
            .createdAt(aiHistory.getCreatedAt())
            .build();
    }

}

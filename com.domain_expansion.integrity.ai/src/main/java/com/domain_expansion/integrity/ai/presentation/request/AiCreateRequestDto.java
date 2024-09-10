package com.domain_expansion.integrity.ai.presentation.request;

import com.domain_expansion.integrity.ai.domain.model.PromptType;

public record AiCreateRequestDto(
    PromptType promptType,
    String question,
    Long userId
) {

}

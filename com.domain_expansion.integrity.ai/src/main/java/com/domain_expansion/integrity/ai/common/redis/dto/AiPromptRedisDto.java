package com.domain_expansion.integrity.ai.common.redis.dto;

import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import com.domain_expansion.integrity.ai.domain.model.PromptType;

public record AiPromptRedisDto(
    PromptType name,
    String prompt
) {

    public static AiPromptRedisDto aiPromptRedisDto(AiPrompt aiPrompt) {
        return new AiPromptRedisDto(aiPrompt.getName(), aiPrompt.getPrompt());
    }

}

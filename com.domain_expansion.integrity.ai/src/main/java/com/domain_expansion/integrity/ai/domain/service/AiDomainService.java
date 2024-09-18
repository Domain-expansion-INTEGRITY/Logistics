package com.domain_expansion.integrity.ai.domain.service;

import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import com.domain_expansion.integrity.ai.domain.model.PromptType;

public interface AiDomainService {

    public String generateAiAnswer(String question, String redisKey);

    public AiPrompt findByTypeAndCheck(PromptType promptType);

}

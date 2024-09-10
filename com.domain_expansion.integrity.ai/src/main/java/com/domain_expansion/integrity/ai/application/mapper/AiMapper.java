package com.domain_expansion.integrity.ai.application.mapper;

import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import org.springframework.stereotype.Component;

@Component
public class AiMapper {

    public AiHistory createDtoToAiHistory(String generatedQuestion, String ksuid,
        AiPrompt aiPrompt) {
        return AiHistory.from(ksuid, generatedQuestion, aiPrompt);
    }

}

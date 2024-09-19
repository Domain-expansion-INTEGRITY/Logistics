package com.domain_expansion.integrity.ai.domain.service;


import com.domain_expansion.integrity.ai.common.exception.AiException;
import com.domain_expansion.integrity.ai.common.message.ExceptionMessage;
import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import com.domain_expansion.integrity.ai.domain.model.PromptType;
import com.domain_expansion.integrity.ai.domain.model.gemini.GeminiClient;
import com.domain_expansion.integrity.ai.domain.model.gemini.GeminiProperty;
import com.domain_expansion.integrity.ai.domain.model.gemini.GeminiRequest;
import com.domain_expansion.integrity.ai.domain.model.gemini.GeminiResponse;
import com.domain_expansion.integrity.ai.domain.repository.aiprompt.AiPromptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AiDomainServiceImpl implements AiDomainService {

    private final GeminiClient geminiClient;
    private final GeminiProperty geminiProperty;
    private final AiPromptRepository aiPromptRepository;

    @Override
    @Cacheable(cacheNames = "gemini", key = "args[1]")
    public String generateAiAnswer(String question, String redisKey) {
        String geminiKey = geminiProperty.key();

        GeminiRequest request = new GeminiRequest(question);

        try {
            GeminiResponse output = geminiClient.findAllByQuery(geminiKey, request);
            return output.getCandidates().get(0).getContent().getParts().get(0)
                .getText();
        } catch (Exception e) {
            // 외부 api 호출 별도 적용
            log.error("Gemini ai 호출 에러" + e);
            throw new AiException(ExceptionMessage.GEMINI_EXCEPTION);
        }

    }

    @Override
    @Cacheable(cacheNames = "promptCache", key = "args[0]")
    public AiPrompt findByTypeAndCheck(PromptType promptType) {
        return aiPromptRepository.findByName(promptType)
            .orElseThrow(() -> new AiException(
                ExceptionMessage.INVALID_PROMPT_TYPE));
    }
}

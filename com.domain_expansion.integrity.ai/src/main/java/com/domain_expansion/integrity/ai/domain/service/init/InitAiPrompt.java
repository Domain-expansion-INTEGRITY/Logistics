package com.domain_expansion.integrity.ai.domain.service.init;

import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import com.domain_expansion.integrity.ai.domain.model.PromptType;
import com.domain_expansion.integrity.ai.domain.repository.aiprompt.AiPromptRepository;
import com.github.ksuid.Ksuid;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitAiPrompt {

    private final AiPromptRepository promptRepository;

    @PostConstruct
    public void initData() {
        for (PromptType value : PromptType.values()) {
            if (!checkExist(value)) {
                AiPrompt aiPrompt = AiPrompt.from(Ksuid.newKsuid().toString(), value,
                    value.getType());
                promptRepository.save(aiPrompt);
            }
        }
    }

    /**
     * prompt 존재하는 지 확인
     */
    public boolean checkExist(PromptType name) {
        Optional<AiPrompt> promptInfo = promptRepository.findByName(name);

        return promptInfo.isPresent();
    }
}

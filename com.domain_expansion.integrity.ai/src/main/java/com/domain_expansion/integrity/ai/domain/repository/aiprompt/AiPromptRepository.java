package com.domain_expansion.integrity.ai.domain.repository.aiprompt;

import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import com.domain_expansion.integrity.ai.domain.model.PromptType;
import com.domain_expansion.integrity.ai.infrasturcture.repository.JpaAiPromptRepository;
import java.util.Optional;

public interface AiPromptRepository extends JpaAiPromptRepository {

    Optional<AiPrompt> findByName(PromptType name);
}

package com.domain_expansion.integrity.ai.infrasturcture.repository;

import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAiPromptRepository extends JpaRepository<AiPrompt, String> {

}

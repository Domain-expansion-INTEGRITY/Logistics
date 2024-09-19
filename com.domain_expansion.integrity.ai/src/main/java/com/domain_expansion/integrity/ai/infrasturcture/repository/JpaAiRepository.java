package com.domain_expansion.integrity.ai.infrasturcture.repository;

import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import com.domain_expansion.integrity.ai.domain.model.PromptType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAiRepository extends JpaRepository<AiHistory, String> {

    Page<AiHistory> findAllByAiPrompt_Name(PromptType name, Pageable pageable);

}

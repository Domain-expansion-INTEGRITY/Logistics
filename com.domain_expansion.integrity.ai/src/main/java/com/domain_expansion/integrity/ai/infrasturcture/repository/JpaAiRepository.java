package com.domain_expansion.integrity.ai.infrasturcture.repository;

import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAiRepository extends JpaRepository<AiHistory, String> {

}

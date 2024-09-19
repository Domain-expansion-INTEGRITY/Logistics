package com.domain_expansion.integrity.ai.application.aiHistory;

import com.domain_expansion.integrity.ai.domain.model.PromptType;
import com.domain_expansion.integrity.ai.presentation.response.AiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiReadService {

    AiResponseDto findAiHistoryById(String aiId);

    Page<AiResponseDto> findAiHistoryList(Pageable pageable, PromptType type);

}

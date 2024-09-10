package com.domain_expansion.integrity.ai.application.aiHistory;

import com.domain_expansion.integrity.ai.presentation.request.AiCreateRequestDto;
import com.domain_expansion.integrity.ai.presentation.response.AiResponseDto;

public interface AiCreateService {

    AiResponseDto createAi(AiCreateRequestDto requestDto);

}

package com.domain_expansion.integrity.ai.application.aiHistory;

import com.domain_expansion.integrity.ai.application.mapper.AiMapper;
import com.domain_expansion.integrity.ai.common.exception.AiException;
import com.domain_expansion.integrity.ai.common.message.ExceptionMessage;
import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import com.domain_expansion.integrity.ai.domain.model.PromptType;
import com.domain_expansion.integrity.ai.domain.repository.aihistory.AiRepository;
import com.domain_expansion.integrity.ai.domain.repository.aiprompt.AiPromptRepository;
import com.domain_expansion.integrity.ai.presentation.request.AiCreateRequestDto;
import com.domain_expansion.integrity.ai.presentation.response.AiResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AiServiceImpl implements AiService {

    private final AiRepository aiRepository;
    private final AiPromptRepository aiPromptRepository;

    private final AiMapper aiMapper;


    @Transactional
    @Override
    public AiResponseDto createAi(AiCreateRequestDto requestDto) {

        AiPrompt aiPrompt = findByIdAndCheck(requestDto.promptType());

        // aiPrompt로 부터 question을 생성해낸다.

        // TODO: 생성된 question으로 변경
        AiHistory ai = aiMapper.createDtoToAiHistory(
            requestDto.question(), Ksuid.newKsuid().toString(), aiPrompt
        );

        // GEMINI AI 호출해서 답을 갖고 온다.

        // TODO: 실제 답을 기록
        ai.updateAnswer("정답을 알려줘");

        AiHistory savedAi = aiRepository.save(ai);

        return AiResponseDto.from(savedAi, requestDto.userId());
    }

    private AiPrompt findByIdAndCheck(PromptType promptType) {
        return aiPromptRepository.findByName(promptType).orElseThrow(() -> new AiException(
            ExceptionMessage.INVALID_PROMPT_TYPE));
    }
}

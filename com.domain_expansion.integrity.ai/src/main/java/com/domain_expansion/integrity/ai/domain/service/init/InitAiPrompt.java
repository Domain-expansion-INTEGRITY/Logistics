package com.domain_expansion.integrity.ai.domain.service.init;

import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import com.domain_expansion.integrity.ai.domain.model.PromptType;
import com.domain_expansion.integrity.ai.domain.repository.aiprompt.AiPromptRepository;
import com.github.ksuid.Ksuid;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitAiPrompt {

    private final JdbcTemplate jdbcTemplate;
    private final AiPromptRepository promptRepository;

    @PostConstruct
    public void init() {
        resetEnumConstraint();
        registerInitPrompt();
    }

    /**
     * 이후 enum이 변경되었을 경우 새로등록하기 위함
     */
    public void resetEnumConstraint() {
        String[] enumValue = new String[PromptType.values().length];

        for (int i = 0; i < enumValue.length; i++) {
            enumValue[i] = "'" + PromptType.values()[i].name() + "'";
        }

        // 제약 삭제 후
        jdbcTemplate.execute(
            "ALTER TABLE p_ai_prompt DROP CONSTRAINT IF EXISTS p_ai_prompt_name_check");

        String enumConstraint = "CHECK (name IN(" + String.join(",", enumValue) + "))";
        // 재등록
        jdbcTemplate.execute(
            "ALTER TABLE p_ai_prompt ADD CONSTRAINT p_ai_prompt_name_check " + enumConstraint
        );
        
    }

    public void registerInitPrompt() {
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

package com.domain_expansion.integrity.ai.domain.model;

import com.domain_expansion.integrity.ai.common.entity.BaseDateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_ai_prompt")
@Getter
@Entity
public class AiPrompt extends BaseDateEntity implements Serializable {

    @Id
    @Column(name = "ai_prompt_id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private PromptType name;

    @Column(columnDefinition = "TEXT")
    private String prompt;

    @Builder(access = AccessLevel.PROTECTED)
    private AiPrompt(String id, PromptType type, String prompt) {
        this.id = id;
        this.name = type;
        this.prompt = prompt;
    }


    public static AiPrompt from(String id, PromptType type, String prompt) {
        return AiPrompt.builder()
            .id(id)
            .type(type)
            .prompt(prompt)
            .build();
    }


}

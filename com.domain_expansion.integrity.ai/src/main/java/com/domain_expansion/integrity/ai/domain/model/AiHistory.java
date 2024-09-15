package com.domain_expansion.integrity.ai.domain.model;

import com.domain_expansion.integrity.ai.common.entity.BaseDateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_ai")
@Getter
@Entity
public class AiHistory extends BaseDateEntity {

    @Id
    @Column(name = "ai_id")
    private String id;

    @Column(length = 1500, nullable = false)
    private String question;

    @Column(length = 500)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_prompt")
    private AiPrompt aiPrompt;

    @Builder(access = AccessLevel.PROTECTED)
    private AiHistory(String id, String question, AiPrompt aiPrompt) {
        this.id = id;
        this.question = question;
        this.aiPrompt = aiPrompt;
    }

    public static AiHistory from(String id, String question, AiPrompt aiPrompt) {

        return AiHistory.builder()
            .id(id)
            .question(question)
            .aiPrompt(aiPrompt)
            .build();
    }

    /**
     * 요청 이후 ai 정답을 만들어준다.
     */
    public void updateAnswer(String answer) {
        this.answer = answer;
    }

}

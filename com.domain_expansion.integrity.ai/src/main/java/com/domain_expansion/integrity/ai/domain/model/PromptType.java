package com.domain_expansion.integrity.ai.domain.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PromptType {

    //TODO: 질문에 맞춰서 조정
    DELIVER_WITH_WEATHER("날씨 정보 저장"),
    HUB_DELIVERY_MESSAGE("허브 담당자 메세지 생성"),
    ;


    private final String type;
}

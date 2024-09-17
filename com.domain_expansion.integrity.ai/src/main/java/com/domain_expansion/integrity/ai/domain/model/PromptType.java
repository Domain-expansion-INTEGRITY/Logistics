package com.domain_expansion.integrity.ai.domain.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PromptType {

    //TODO: 질문에 맞춰서 조정
    DELIVER_WITH_WEATHER("""
        위에 날씨 정보와 배송정보를 갖고 배송기사에게 전달할 메시지를 만들어줘
         1. 한국어로 작성해줘
         2. 200자 이내로 만들어줘"""),
    DELIVERY_SEQUENCE("""
        출발 허브에서 시작해서 배송 예정 주소 목록으로 배송해야 하는데 거리가 짧은 순서대로 알려줘
         1. 결과값은 지나가는 순서를 설명없이 [{...}]과 같이 JSON List 형태로만 보내줘
         2. JSON field는 starting, ending, latitude, longitude 입니다.
         3. 위에서 작성되는 latitude와 longitude는 도착지점의 위도 경도야"""),
    HUB_DELIVERY_MESSAGE("""
        위의 배송정보를 갖고 허브 배송 담당자를 위한 메세지를 만들어줘.
        출발 배송지도 메세지에 넣어줘
         1. 한국어로 작성해줘
         2. 200자 이내로 만들어줘"""),
    ;


    private final String type;
}

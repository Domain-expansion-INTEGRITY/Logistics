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
            위의 주소들을 Google Maps API를 이용하여 출발지에서 목적지까지의 거리가 짧은 순서대로 정렬하여 다음과 같은 형식으로 결과를 설명없이 JSON으로만 반환해주세요
            출발지에서 출발하여 목적지로 이동할 때, 중간에 출발지를 다시 경유하지 않고 목적지 간의 경로를 설정해 주세요.
            JSON field는 starting, ending, latitude, longitude 입니다.
            예시:
            ========================================================
            - 출발 허브 주소: "울산 남구 중앙로 201"
            - 배송 예정 주소: ["시흥시 시흥대로 405", "시흥시 시흥대로 409"]
                        
            결과:
            [
                {
                    "starting": "울산 남구 중앙로 201",
                    "ending": "시흥시 시흥대로 405",
                    "latitude": 37.440333,
                    "longitude": 126.811483
                },
                {
                    "starting": "시흥시 시흥대로 405",
                    "ending": "시흥시 시흥대로 409",
                    "latitude": 37.441245,
                    "longitude": 126.812001
                }
            ]
            ==========================================================
            
            """),


    HUB_DELIVERY_MESSAGE("""
        위의 배송정보를 갖고 허브 배송 담당자를 위한 메세지를 만들어줘.
        출발 배송지도 메세지에 넣어줘
         1. 한국어로 작성해줘
         2. 200자 이내로 만들어줘"""),
    ;


    private final String type;
}

package com.domain_expansion.integrity.delivery.application.client.response;

import java.util.List;
import lombok.Getter;

@Getter
public class AiResponseData extends CommonResponseData {

    private final AiResponseDto data;

    public AiResponseData(Boolean success, String message, AiResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public record AiResponseDto(
        String aiId,
        String question,
        List<AiResponseAnswerDto> answer,
        Long userId
    ) {

        public record AiResponseAnswerDto(
                String starting,
                String ending,
                Double latitude,
                Double longitude
        ) {

        }
    }
}

package com.domain_expansion.integrity.hub.presentation.response;


import static lombok.AccessLevel.PRIVATE;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import lombok.Builder;

@Builder(access = PRIVATE)
public record HubCreateResponseDto(
    String hubId
) {

    public static HubCreateResponseDto from(Hub hub) {
        return HubCreateResponseDto.builder()
                .hubId(hub.getHubId())
                .build();
    }
}

package com.domain_expansion.integrity.hub.presentation.response;


import com.domain_expansion.integrity.hub.domain.model.Hub;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record HubResponseDto(
        String hubId,
        Long userId,
        String name,
        String address,
        double latitude,
        double longitude
) {


    public static HubResponseDto from(Hub hub) {
        return HubResponseDto.builder()
                .hubId(hub.getHubId())
                .userId(hub.getUserId())
                .name(hub.getName())
                .address(hub.getHubAddress().getAddress())
                .latitude(hub.getHubLatitude().getLatitude())
                .longitude(hub.getHubLongitude().getLongitude())
                .build();
    }
}

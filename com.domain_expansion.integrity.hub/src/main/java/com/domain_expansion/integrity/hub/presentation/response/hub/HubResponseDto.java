package com.domain_expansion.integrity.hub.presentation.response.hub;


import com.domain_expansion.integrity.hub.domain.model.Hub;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record HubResponseDto(
        String hubId,
        Long userId,
        String name,
        String address,
        double latitude,
        double longitude,
        List<HubDeliverManResponseDto> deliveryManList
) {

    public static HubResponseDto from(Hub hub) {
        return HubResponseDto.builder()
                .hubId(hub.getHubId())
                .userId(hub.getUserId())
                .name(hub.getName())
                .address(hub.getAddress())
                .latitude(hub.getHubLatitude().getLatitude())
                .longitude(hub.getHubLongitude().getLongitude())
                .build();
    }

    public static HubResponseDto from(Hub hub, List<HubDeliverManResponseDto> deliveryManList) {
        return HubResponseDto.builder()
                .hubId(hub.getHubId())
                .userId(hub.getUserId())
                .name(hub.getName())
                .address(hub.getAddress())
                .latitude(hub.getHubLatitude().getLatitude())
                .longitude(hub.getHubLongitude().getLongitude())
                .deliveryManList(deliveryManList)
                .build();
    }
}

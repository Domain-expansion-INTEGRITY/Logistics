package com.domain_expansion.integrity.hub.presentation.response.deliveryMan;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record HubDeliveryManCreateResposeDto(
        List<String> deliveryManIds
) {

    public static HubDeliveryManCreateResposeDto of(List<String> deliveryManId) {
        return HubDeliveryManCreateResposeDto.builder()
                .deliveryManIds(deliveryManId)
                .build();
    }
}

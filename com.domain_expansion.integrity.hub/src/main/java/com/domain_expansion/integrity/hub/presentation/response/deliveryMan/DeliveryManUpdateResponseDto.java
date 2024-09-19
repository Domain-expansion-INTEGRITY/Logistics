package com.domain_expansion.integrity.hub.presentation.response.deliveryMan;

import com.domain_expansion.integrity.hub.domain.model.DeliveryMan;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DeliveryManUpdateResponseDto(
        String DeliveryManId,
        Long userId
) {

    public static DeliveryManUpdateResponseDto from(DeliveryMan deliveryMan) {
        return DeliveryManUpdateResponseDto.builder()
                .DeliveryManId(deliveryMan.getDeliveryManId())
                .userId(deliveryMan.getUserId())
                .build();
    }
}

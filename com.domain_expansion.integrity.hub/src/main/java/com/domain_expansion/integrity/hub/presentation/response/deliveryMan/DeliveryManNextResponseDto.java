package com.domain_expansion.integrity.hub.presentation.response.deliveryMan;

import com.domain_expansion.integrity.hub.domain.model.DeliveryMan;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DeliveryManNextResponseDto(
        String deliveryManId,
        Long userId
) {

    public static DeliveryManNextResponseDto from(DeliveryMan deliveryMan) {
        return DeliveryManNextResponseDto.builder()
                .deliveryManId(deliveryMan.getDeliveryManId())
                .userId(deliveryMan.getUserId())
                .build();
    }
}

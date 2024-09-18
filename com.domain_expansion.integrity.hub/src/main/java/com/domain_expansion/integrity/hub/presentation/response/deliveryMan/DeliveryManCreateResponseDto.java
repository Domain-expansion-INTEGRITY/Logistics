package com.domain_expansion.integrity.hub.presentation.response.deliveryMan;

import com.domain_expansion.integrity.hub.domain.model.DeliveryMan;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DeliveryManCreateResponseDto(
        String DeliveryManId
) {

    public static DeliveryManCreateResponseDto from(DeliveryMan deliveryMan) {
        return DeliveryManCreateResponseDto.builder().DeliveryManId(deliveryMan.getDeliveryManId()).build();
    }
}

package com.domain_expansion.integrity.hub.presentation.response.deliveryMan;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DeliveryManResponseDto(
        String deliveryManId,
        Long userId,
        String hubId
) {
    public static DeliveryManResponseDto from(String deliveryManId, Long userId, String hubId) {
        return DeliveryManResponseDto.builder()
                .deliveryManId(deliveryManId)
                .userId(userId)
                .hubId(hubId)
                .build();
    }

    public static DeliveryManResponseDto from(String deliveryManId, Long userId) {
        return DeliveryManResponseDto.builder()
                .deliveryManId(deliveryManId)
                .userId(userId)
                .build();
    }
}

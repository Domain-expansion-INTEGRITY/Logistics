package com.domain_expansion.integrity.hub.presentation.request.deliveryMan;

public record DeliveryManSearchCondition(
        String deliveryManId,
        Long userId,
        String hubName
) {

}

package com.domain_expansion.integrity.delivery.presentation.request;

import com.domain_expansion.integrity.delivery.domain.model.constant.DeliveryStatus;

public record DeliverySearchCondition(
        String deliveryManId,
        DeliveryStatus status,
        String address,
        String receiver
) {

}

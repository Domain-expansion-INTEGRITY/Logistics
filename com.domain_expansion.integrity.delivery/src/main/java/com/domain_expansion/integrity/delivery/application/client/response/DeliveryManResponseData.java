package com.domain_expansion.integrity.delivery.application.client.response;

import lombok.Getter;

@Getter
public class DeliveryManResponseData extends CommonResponseData {

    private final DeliveryManResponseDto data;

    public DeliveryManResponseData(Boolean success, String message, DeliveryManResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public record DeliveryManResponseDto(
            String deliveryManId,
            String userId,
            String hubId
    ) {

    }
}

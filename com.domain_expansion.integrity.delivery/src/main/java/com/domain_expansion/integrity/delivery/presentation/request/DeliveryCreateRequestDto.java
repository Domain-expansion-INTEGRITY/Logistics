package com.domain_expansion.integrity.delivery.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record DeliveryCreateRequestDto(

        @NotBlank
        String startHubId,

        @NotBlank
        String endHubId,

        @NotBlank
        String orderId,

        @NotBlank
        String address,

        @NotBlank
        String receiver,

        @NotBlank
        String receiverSlackId
) {

}

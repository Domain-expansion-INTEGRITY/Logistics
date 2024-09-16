package com.domain_expansion.integrity.hub.presentation.response.deliveryMan;

import java.util.List;

public record DeliveryManGetResponseDto(
        List<DeliveryManResponseDto> deliveryMans
) {

}

package com.domain_expansion.integrity.hub.presentation.request.deliveryMan;

import java.util.List;

public record HubDeliveryManCreateRequestDto(
        List<HubDeliveryRequsetDto> deliveryManIds
) {

}


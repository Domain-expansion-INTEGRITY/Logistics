package com.domain_expansion.integrity.delivery.presentation.request;

import java.util.List;

public record DeliveryUpdateRequestDto(
        List<DeliveryProductRequestDto> productList
) {

}

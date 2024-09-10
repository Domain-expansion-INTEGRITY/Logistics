package com.domain_expansion.integrity.order.presentation.request;

import java.util.List;

public record OrderUpdateRequestDto(
        List<OrderProductRequestDto> productList
) {

}

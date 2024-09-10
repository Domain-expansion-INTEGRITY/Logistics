package com.domain_expansion.integrity.order.presentation.request;

public record OrderProductRequestDto(
        String productId,
        Integer count
) {

}

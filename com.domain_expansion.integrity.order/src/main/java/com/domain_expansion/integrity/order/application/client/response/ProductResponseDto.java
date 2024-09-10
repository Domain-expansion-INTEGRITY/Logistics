package com.domain_expansion.integrity.order.application.client.response;

public record ProductResponseDto(
        String companyId,
        String companyName,
        String productId,
        String productName,
        Integer stock
) {

}

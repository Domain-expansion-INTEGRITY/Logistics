package com.domain_expansion.integrity.order.application.client.response;

import lombok.Getter;

@Getter
public class ProductResponse extends CommonResponseData {

    private final ProductResponseDto data;

    public ProductResponse(boolean success, String message, ProductResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public record ProductResponseDto(
            String companyId,
            String companyName,
            String productId,
            String productName,
            Integer stock
    ) {

    }
}

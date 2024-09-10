package com.domain_expansion.integrity.order.application.client.response;

import lombok.Getter;

@Getter
public class ProductResponse extends CommonResponseData {

    private final ProductResponseDto productResponseDto;

    public ProductResponse(boolean success, String message, ProductResponseDto productResponseDto) {
        super(success, message);
        this.productResponseDto = productResponseDto;
    }
}

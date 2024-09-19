package com.domain_expansion.integrity.product.presentation.response;

import static lombok.AccessLevel.PRIVATE;

import com.domain_expansion.integrity.product.domain.model.Product;
import lombok.Builder;

@Builder(access = PRIVATE)
public record ProductResponseDto (
        String companyId,
        String companyName,
        String productId,
        String productName,
        Integer stock
) {

    public static ProductResponseDto from(Product product) {

        return ProductResponseDto.builder()
                .companyId(product.getCompany().getCompanyId())
                .companyName(product.getCompany().getCompanyName())
                .productId(product.getProductId())
                .productName(product.getName().getValue())
                .stock(product.getStock().getValue())
                .build();
    }
}

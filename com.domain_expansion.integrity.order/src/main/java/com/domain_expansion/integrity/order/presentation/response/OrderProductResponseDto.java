package com.domain_expansion.integrity.order.presentation.response;

import com.domain_expansion.integrity.order.domain.model.OrderProduct;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record OrderProductResponseDto(
        String productId,
        String productName,
        Integer count
) {

    public static OrderProductResponseDto from(OrderProduct orderProduct) {

        return OrderProductResponseDto.builder()
                .productId(orderProduct.getProductInfo().getProductId())
                .productName(orderProduct.getProductInfo().getProductName())
                .count(orderProduct.getCount())
                .build();
    }
}

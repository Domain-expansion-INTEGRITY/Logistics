package com.domain_expansion.integrity.order.presentation.response;

import static lombok.AccessLevel.PRIVATE;

import com.domain_expansion.integrity.order.domain.model.Order;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder(access = PRIVATE)
public record OrderResponseDto(
        String orderId,
        String sellerCompanyId,
        String buyerCompanyId,
        List<OrderProductResponseDto> productList
) {

    public static OrderResponseDto from(Order order) {

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .sellerCompanyId(order.getSellerCompanyId())
                .buyerCompanyId(order.getBuyerCompanyId())
                .productList(order.getOrderProducts()
                        .stream().map(OrderProductResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}

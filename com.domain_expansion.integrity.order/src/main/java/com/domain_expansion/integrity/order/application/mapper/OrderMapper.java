package com.domain_expansion.integrity.order.application.mapper;

import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.presentation.request.OrderCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order orderCreateRequestDtoAndOrderIdToOrder(
            OrderCreateRequestDto requestDto, String orderId
    ) {

        return Order.of(
                orderId,
                false,
                requestDto.sellerCompanyId(),
                requestDto.buyerCompanyId()
        );
    }
}

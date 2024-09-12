package com.domain_expansion.integrity.order.domain.service;

import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.presentation.request.OrderProductRequestDto;
import com.domain_expansion.integrity.order.presentation.request.OrderUpdateRequestDto;
import com.domain_expansion.integrity.order.presentation.response.OrderResponseDto;
import java.util.List;

public interface OrderDomainService {

    Order addOrderProductAndSave(Order order, List<OrderProductRequestDto> orderProductRequestDtos);

    String createOrderId();

    Order updateOrder(List<OrderProductRequestDto> requestDtos, Order order);
}

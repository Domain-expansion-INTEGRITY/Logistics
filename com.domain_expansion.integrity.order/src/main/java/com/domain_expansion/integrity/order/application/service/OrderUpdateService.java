package com.domain_expansion.integrity.order.application.service;

import com.domain_expansion.integrity.order.presentation.request.OrderUpdateRequestDto;
import com.domain_expansion.integrity.order.presentation.response.OrderResponseDto;

public interface OrderUpdateService {

    OrderResponseDto updateOrder(OrderUpdateRequestDto requestDto, String OrderId);

}

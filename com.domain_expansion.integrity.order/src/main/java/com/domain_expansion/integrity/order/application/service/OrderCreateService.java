package com.domain_expansion.integrity.order.application.service;

import com.domain_expansion.integrity.order.presentation.request.OrderCreateRequestDto;
import com.domain_expansion.integrity.order.presentation.response.OrderResponseDto;

public interface OrderCreateService {

    OrderResponseDto createOrder(OrderCreateRequestDto requestDto);

}

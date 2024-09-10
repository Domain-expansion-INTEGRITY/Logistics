package com.domain_expansion.integrity.order.application.service;

import com.domain_expansion.integrity.order.presentation.response.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReadService {

    OrderResponseDto getOrder(String requestDto);

    Page<OrderResponseDto> getOrders(Pageable pageable);

}

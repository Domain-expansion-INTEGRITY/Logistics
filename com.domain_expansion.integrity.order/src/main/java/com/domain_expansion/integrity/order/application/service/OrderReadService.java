package com.domain_expansion.integrity.order.application.service;

import com.domain_expansion.integrity.order.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.order.presentation.request.OrderSearchCondition;
import com.domain_expansion.integrity.order.presentation.response.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReadService {

    OrderResponseDto getOrder(String orderId, UserDetailsImpl userDetails);

    Page<OrderResponseDto> getOrdersByCondition(Pageable pageable, UserDetailsImpl userDetails, OrderSearchCondition condition);

}

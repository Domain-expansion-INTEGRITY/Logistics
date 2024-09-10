package com.domain_expansion.integrity.order.application.service;

import com.domain_expansion.integrity.order.application.mapper.OrderMapper;
import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.domain.repository.OrderRepository;
import com.domain_expansion.integrity.order.domain.service.OrderDomainService;
import com.domain_expansion.integrity.order.presentation.request.OrderCreateRequestDto;
import com.domain_expansion.integrity.order.presentation.request.OrderUpdateRequestDto;
import com.domain_expansion.integrity.order.presentation.response.OrderResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImplV1 implements OrderService {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto) {

        String productId = Ksuid.newKsuid().toString();

        Order order = orderMapper.orderCreateRequestDtoAndOrderIdToOrder(requestDto, productId);

        // TODO 배송 동시에 생성하기

        return OrderResponseDto.from(orderDomainService.addOrderProductAndSave(order, requestDto.productList()));
    }

    @Override
    public void deleteOrder(String OrderId) {

    }

    @Override
    public OrderResponseDto getOrder(String requestDto) {
        return null;
    }

    @Override
    public Page<OrderResponseDto> getOrders(Pageable pageable) {
        return null;
    }

    @Override
    public OrderResponseDto updateOrder(OrderUpdateRequestDto requestDto, String OrderId) {
        return null;
    }
}

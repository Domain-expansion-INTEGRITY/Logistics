package com.domain_expansion.integrity.order.application.service;

import com.domain_expansion.integrity.order.application.client.CompanyClient;
import com.domain_expansion.integrity.order.application.client.HubClient;
import com.domain_expansion.integrity.order.application.client.response.CompaniesResponseData;
import com.domain_expansion.integrity.order.application.client.response.CompaniesResponseData.CompaniesResponseDto;
import com.domain_expansion.integrity.order.application.client.response.CompanyResponseData;
import com.domain_expansion.integrity.order.application.client.response.HubResponseData;
import com.domain_expansion.integrity.order.application.mapper.OrderMapper;
import com.domain_expansion.integrity.order.common.exception.OrderException;
import com.domain_expansion.integrity.order.common.message.ExceptionMessage;
import com.domain_expansion.integrity.order.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.domain.repository.OrderQueryRepository;
import com.domain_expansion.integrity.order.domain.repository.OrderRepository;
import com.domain_expansion.integrity.order.domain.service.OrderDomainService;
import com.domain_expansion.integrity.order.presentation.request.OrderCreateRequestDto;
import com.domain_expansion.integrity.order.presentation.request.OrderSearchCondition;
import com.domain_expansion.integrity.order.presentation.request.OrderUpdateRequestDto;
import com.domain_expansion.integrity.order.presentation.response.OrderResponseDto;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImplV1 implements OrderService {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CompanyClient companyClient;
    private final HubClient hubClient;
    private final OrderQueryRepository orderQueryRepository;

    @Override
    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto) {

        String productId = orderDomainService.createOrderId();

        Order order = orderMapper.toOrder(requestDto, productId);

        // TODO 배송 동시에 생성하기

        return OrderResponseDto.from(
                orderDomainService.addOrderProductAndSave(order, requestDto.productList()));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(String orderId, UserDetailsImpl userDetails) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderException(ExceptionMessage.NOT_FOUND_ORDER)
        );

        if (!userDetails.isMaster()) {

            if (userDetails.isHubCompany()) {
                ResponseEntity<CompanyResponseData> response = companyClient.getCompanyByUserId(
                        userDetails.getUserId());
                String companyId = response.getBody().getData().companyId();
                if (!order.validateByCompanyId(companyId)) {
                    throw new OrderException(ExceptionMessage.GUARD);
                }
            } else if (userDetails.isHubManager()) {
                ResponseEntity<HubResponseData> hubResponse = hubClient.getHubByUserId(
                        userDetails.getUserId());
                String hubId = hubResponse.getBody().getData().hubId();
                ResponseEntity<CompaniesResponseData> companiesResponse = companyClient.getCompaniesByHubId(
                        hubId);
                Set<String> companyIdsSet = companiesResponse.getBody().getData().stream().map(CompaniesResponseDto::companyId).collect(
                        Collectors.toSet());
                if (!order.validateByCompanyIds(companyIdsSet)) {
                    throw new OrderException(ExceptionMessage.GUARD);
                }
            }

            if (userDetails.isHubDeliveryMan() || userDetails.isCompanyDeliveryMan()) {
                // ResponseEntity<DeliveryResponseData> response = deliveryClient.getOrderIdsByUserId(userDetails.getUserId());
                // Set<String> orderIds = response.getData().getOrderIds();
                // if (!orderIds.containsKey(order.getOrderId())) {
                //     throw new OrderException(ExceptionMessage.GUARD);
                // }
                throw new OrderException(ExceptionMessage.GUARD);
                // TODO 추후 구현 (이걸 사실 구현해야할지는 모르겠어용)
            }
        }

        return OrderResponseDto.from(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrdersByCondition(Pageable pageable,
            UserDetailsImpl userDetails, OrderSearchCondition condition) {

        if (!userDetails.isMaster()) {

            if (userDetails.isHubCompany()) {
                 ResponseEntity<CompanyResponseData> response = companyClient.getCompanyByUserId(userDetails.getUserId());
                 String companyId = response.getBody().getData().companyId();
                 return orderQueryRepository.findAllByCompanyIdAndCondition(companyId, pageable, condition).map(OrderResponseDto::from);
            }

            if (userDetails.isHubManager()) {
                 ResponseEntity<HubResponseData> hubResponse = hubClient.getHubByUserId(userDetails.getUserId());
                 String hubId = hubResponse.getBody().getData().hubId();
                 ResponseEntity<CompaniesResponseData> companiesResponse = companyClient.getCompaniesByHubId(hubId);
                 List<String> companyIds = companiesResponse.getBody().getData().stream().map(CompaniesResponseDto::companyId).collect(Collectors.toList());
                 return orderQueryRepository.findAllByCompanyIdsAndCondition(companyIds, pageable, condition).map(OrderResponseDto::from); // In 절 사용
            }

            if (userDetails.isHubDeliveryMan() || userDetails.isCompanyDeliveryMan()) {
                // ResponseEntity<DeliveryResponseData> response = deliveryClient.getOrderIdsByUserId(userDetails.getUserId());
                // Set<String> orderIds = response.getData().getOrderIds();
                // return orderQueryRepository.findAllByOrderIdsAndCondition(orderIds, pageable, condition).map(OrderResponseDto::from); // In 절 사용
                // TODO 추후 구현 (이걸 사실 구현해야할지는 모르겠어용)
                 throw new OrderException(ExceptionMessage.GUARD);
            }
        }

        return orderQueryRepository.findAllByCondition(pageable, condition).map(OrderResponseDto::from);
    }

    @Override
    public OrderResponseDto updateOrder(OrderUpdateRequestDto requestDto, String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderException(ExceptionMessage.NOT_FOUND_ORDER)
        );

        return OrderResponseDto.from(
                orderDomainService.updateOrder(requestDto.productList(), order));
    }

    @Override
    public void deleteOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderException(ExceptionMessage.NOT_FOUND_ORDER)
        );

        order.delete();
    }
}

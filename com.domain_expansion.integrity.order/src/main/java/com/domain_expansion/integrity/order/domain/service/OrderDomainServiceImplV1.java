package com.domain_expansion.integrity.order.domain.service;

import com.domain_expansion.integrity.order.application.client.ProductClient;
import com.domain_expansion.integrity.order.application.client.response.ProductResponse;
import com.domain_expansion.integrity.order.common.exception.OrderException;
import com.domain_expansion.integrity.order.common.message.ExceptionMessage;
import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.domain.model.OrderProduct;
import com.domain_expansion.integrity.order.domain.model.info.ProductInfo;
import com.domain_expansion.integrity.order.domain.repository.OrderRepository;
import com.domain_expansion.integrity.order.presentation.request.OrderProductRequestDto;
import com.github.ksuid.Ksuid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDomainServiceImplV1 implements OrderDomainService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Override
    public Order addOrderProductAndSave(Order order, List<OrderProductRequestDto> orderProductRequestDtos) {

        for (OrderProductRequestDto orderProductRequestDto : orderProductRequestDtos) {
            ProductResponse productResponse = productClient.getProductById(
                    orderProductRequestDto.productId());

            if (!productResponse.isSuccess()) {
                throw new OrderException(ExceptionMessage.PRODUCT_SERVER_ERROR);
            }

            String orderProductId = Ksuid.newKsuid().toString();

            order.addOrderProduct(
                    new OrderProduct(
                            orderProductId,
                            new ProductInfo(
                                    productResponse.getProductResponseDto().productId(),
                                    productResponse.getProductResponseDto().productName()
                            ),
                            order,
                            orderProductRequestDto.count()
                    )
            );
        }

        return orderRepository.save(order);
    }

    @Override
    public String createOrderId() {

        return Ksuid.newKsuid().toString();
    }

    @Override
    public Order updateOrder(List<OrderProductRequestDto> requestDtos, Order order) {

        order.clearOrderProducts();

        for (OrderProductRequestDto orderProductRequestDto : requestDtos) {
            ProductResponse productResponse = productClient.getProductById(orderProductRequestDto.productId());
            String orderProductId = Ksuid.newKsuid().toString();

            order.addOrderProduct(
                    new OrderProduct(
                            orderProductId,
                            new ProductInfo(
                                    productResponse.getProductResponseDto().productId(),
                                    productResponse.getProductResponseDto().productName()
                            ),
                            order,
                            orderProductRequestDto.count()
                    )
            );
        }

        return order;
    }
}

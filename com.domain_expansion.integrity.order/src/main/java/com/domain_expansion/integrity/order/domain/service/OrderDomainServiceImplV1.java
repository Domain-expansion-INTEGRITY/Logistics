package com.domain_expansion.integrity.order.domain.service;

import com.domain_expansion.integrity.order.application.client.ProductClient;
import com.domain_expansion.integrity.order.application.client.response.ProductResponse;
import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.domain.model.OrderProduct;
import com.domain_expansion.integrity.order.domain.model.info.ProductInfo;
import com.domain_expansion.integrity.order.domain.repository.OrderRepository;
import com.domain_expansion.integrity.order.presentation.request.OrderProductRequestDto;
import com.github.ksuid.Ksuid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDomainServiceImplV1 implements OrderDomainService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Override
    public Order addOrderProduct(Order order, List<OrderProductRequestDto> orderProductRequestDtos) {

        for (OrderProductRequestDto orderProductRequestDto : orderProductRequestDtos) {
            ResponseEntity<ProductResponse> productResponse = productClient.getProductById(
                    orderProductRequestDto.productId());

            String orderProductId = Ksuid.newKsuid().toString();

            ProductResponse body = productResponse.getBody();

            OrderProduct orderProduct = new OrderProduct(
                    orderProductId,
                    new ProductInfo(
                            body.getData().productId(),
                            body.getData().productName()
                    ),
                    orderProductRequestDto.count()
            );

            order.addOrderProduct(orderProduct);
        }

        return order;
    }

    @Override
    public String createOrderId() {

        return Ksuid.newKsuid().toString();
    }

    @Override
    public Order updateOrder(List<OrderProductRequestDto> requestDtos, Order order) {

        order.clearOrderProducts();

        for (OrderProductRequestDto orderProductRequestDto : requestDtos) {
            ResponseEntity<ProductResponse> productResponse = productClient.getProductById(orderProductRequestDto.productId());
            String orderProductId = Ksuid.newKsuid().toString();

            ProductResponse body = productResponse.getBody();

            OrderProduct orderProduct = new OrderProduct(
                    orderProductId,
                    new ProductInfo(
                            body.getData().productId(),
                            body.getData().productName()
                    ),
                    orderProductRequestDto.count()
            );

            order.addOrderProduct(orderProduct);
        }

        return order;
    }
}

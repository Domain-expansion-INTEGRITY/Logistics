package com.domain_expansion.integrity.order.application.service.dto;

import com.domain_expansion.integrity.order.application.client.response.CompanyResponseData.CompanyResponseDto;
import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.presentation.request.OrderCreateRequestDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DeliveryCreateRequestDto(

        @NotBlank
        String startHubId,

        @NotBlank
        String endHubId,

        @NotBlank
        String orderId,

        @NotBlank
        String address,

        @NotBlank
        String receiver,

        @NotBlank
        String receiverSlackId
) {

        public static DeliveryCreateRequestDto of(Order order, CompanyResponseDto sellerCompanyResponse,
                CompanyResponseDto buyerCompanyResponse, OrderCreateRequestDto requestDto) {

                return DeliveryCreateRequestDto.builder()
                        .startHubId(sellerCompanyResponse.hubId())
                        .endHubId(buyerCompanyResponse.hubId())
                        .orderId(order.getOrderId())
                        .address(requestDto.address())
                        .receiver(requestDto.receiver())
                        .receiverSlackId(requestDto.receiverSlackId())
                        .build();
        }
}

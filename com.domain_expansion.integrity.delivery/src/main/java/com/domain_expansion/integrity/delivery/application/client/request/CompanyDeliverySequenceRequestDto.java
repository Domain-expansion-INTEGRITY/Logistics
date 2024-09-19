package com.domain_expansion.integrity.delivery.application.client.request;

import com.domain_expansion.integrity.delivery.application.client.response.HubResponseData.HubResponseDto;
import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CompanyDeliverySequenceRequestDto(
        String hubId,
        String hubAddress,
        List<DeliveryList> deliveryList,
        String userId
) {

    @Builder(access = AccessLevel.PRIVATE)
    public record DeliveryList(
            String deliveryId,
            String address
    ) {

        public static DeliveryList from(Delivery delivery) {

            return DeliveryList.builder()
                    .deliveryId(delivery.getDeliveryId())
                    .address(delivery.getAddress())
                    .build();
        }
    }

    public static CompanyDeliverySequenceRequestDto of(List<Delivery> deliveries, HubResponseDto hubResponseDto) {

        List<DeliveryList> deliveryLists = deliveries.stream()
                .map(DeliveryList::from)
                .collect(Collectors.toList());

        Delivery firstDelivery = deliveries.get(0);

        return CompanyDeliverySequenceRequestDto.builder()
                .hubId(firstDelivery.getEndHubId())
                .hubAddress(hubResponseDto.address())
                .deliveryList(deliveryLists)
                .userId(firstDelivery.getHubDeliveryManId())
                .build();
    }
}

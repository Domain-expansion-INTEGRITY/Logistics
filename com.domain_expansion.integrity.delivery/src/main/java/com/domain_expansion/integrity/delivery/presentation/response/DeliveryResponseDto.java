package com.domain_expansion.integrity.delivery.presentation.response;

import static lombok.AccessLevel.PRIVATE;

import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;

@Builder(access = PRIVATE)
public record DeliveryResponseDto(
        @JsonInclude(Include.NON_NULL)
        String deliveryManId,
        String status,
        String startHubId,
        String endHubId,
        String address,
        String receiver,
        String receiverSlackId,
        Integer exDistance,
        Integer exDuration,
        @JsonInclude(Include.NON_NULL)
        Integer distance,
        @JsonInclude(Include.NON_NULL)
        Integer duration
) {

    public static DeliveryResponseDto from(Delivery delivery) {
        return DeliveryResponseDto.builder()
                .deliveryManId(delivery.getDeliveryManId())
                .status(delivery.getStatus().name())
                .startHubId(delivery.getStartHubId())
                .endHubId(delivery.getEndHubId())
                .address(delivery.getAddress())
                .receiver(delivery.getReceiver())
                .receiverSlackId(delivery.getReceiverSlackId())
//                .exDistance() // TODO 예상 대기시간 구하기
//                .exDuration()
                //.distance()
                //.duration()
                .build();
    }
}

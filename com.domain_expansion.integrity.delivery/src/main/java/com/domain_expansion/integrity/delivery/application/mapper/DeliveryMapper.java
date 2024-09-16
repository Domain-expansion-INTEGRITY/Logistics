package com.domain_expansion.integrity.delivery.application.mapper;

import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import com.domain_expansion.integrity.delivery.domain.model.constant.DeliveryStatus;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    public Delivery toDelivery(
            DeliveryCreateRequestDto requestDto, String deliveryId
    ) {

        return Delivery.of(
                deliveryId,
                DeliveryStatus.WAITING,
                requestDto.orderId(),
                requestDto.startHubId(),
                requestDto.endHubId(),
                requestDto.address(),
                requestDto.receiver(),
                requestDto.receiverSlackId(),
                false
        );
    }
}

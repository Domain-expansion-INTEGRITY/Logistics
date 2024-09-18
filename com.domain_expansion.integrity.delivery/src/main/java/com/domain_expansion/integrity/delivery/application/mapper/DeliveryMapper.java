package com.domain_expansion.integrity.delivery.application.mapper;

import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import com.domain_expansion.integrity.delivery.domain.model.DeliveryHistory;
import com.domain_expansion.integrity.delivery.domain.model.constant.DeliveryStatus;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryCreateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryHistoryUpdateRequestDto;
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

    public DeliveryHistory toDeliveryHistory(DeliveryHistoryUpdateRequestDto requestDto, Delivery delivery, String deliveryHistoryId) {

        return DeliveryHistory.of(
                requestDto.exDistance(),
                requestDto.exDuration(),
                false,
                delivery.getHubDeliveryManId(),
                deliveryHistoryId
        );
    }
}

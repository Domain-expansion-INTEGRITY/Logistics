package com.domain_expansion.integrity.delivery.application.service;

import com.domain_expansion.integrity.delivery.presentation.request.DeliveryDeliveryManUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryHistoryUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryHubDeliveryManUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.response.DeliveryResponseDto;

public interface DeliveryUpdateService {

    DeliveryResponseDto updateDelivery(DeliveryUpdateRequestDto requestDto, String deliveryId);

    DeliveryResponseDto updateDeliveryDeliveryMan(DeliveryDeliveryManUpdateRequestDto requestDto, String deliveryId);

    DeliveryResponseDto updateDeliveryHubDeliveryMan(
            DeliveryHubDeliveryManUpdateRequestDto requestDto, String deliveryId);

    void updateDeliveryHistory(DeliveryHistoryUpdateRequestDto requestDto, String deliveryId);
}

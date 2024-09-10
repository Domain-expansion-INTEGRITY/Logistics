package com.domain_expansion.integrity.delivery.application.service;

import com.domain_expansion.integrity.delivery.presentation.request.DeliveryUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.response.DeliveryResponseDto;

public interface DeliveryUpdateService {

    DeliveryResponseDto updateDelivery(DeliveryUpdateRequestDto requestDto, String deliveryId);

}

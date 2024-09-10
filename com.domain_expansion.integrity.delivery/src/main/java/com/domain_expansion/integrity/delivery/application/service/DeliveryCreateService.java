package com.domain_expansion.integrity.delivery.application.service;

import com.domain_expansion.integrity.delivery.presentation.request.DeliveryCreateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.response.DeliveryResponseDto;

public interface DeliveryCreateService {

    DeliveryResponseDto createDelivery(DeliveryCreateRequestDto requestDto);

}

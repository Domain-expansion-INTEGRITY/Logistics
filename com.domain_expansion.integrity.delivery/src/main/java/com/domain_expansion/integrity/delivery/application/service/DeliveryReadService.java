package com.domain_expansion.integrity.delivery.application.service;

import com.domain_expansion.integrity.delivery.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.delivery.presentation.request.DeliverySearchCondition;
import com.domain_expansion.integrity.delivery.presentation.response.DeliveryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryReadService {

    DeliveryResponseDto getDelivery(String requestDto);

    Page<DeliveryResponseDto> getDeliveries(Pageable pageable, DeliverySearchCondition searchCondition,
            UserDetailsImpl userDetails);

}

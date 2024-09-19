package com.domain_expansion.integrity.hub.application.service.deliveryman;

import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManNextResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubDeliveryManReadService {

    DeliveryManResponseDto getDeliveryManById(String id, UserDetailsImpl userDetails);

    Page<DeliveryManResponseDto> getAllDeliveryMans(DeliveryManSearchCondition searchDto,Pageable pageable,UserDetailsImpl userDetails);

    DeliveryManNextResponseDto findNextDeliveryMan();
}

package com.domain_expansion.integrity.delivery.application.service;

import com.domain_expansion.integrity.delivery.application.mapper.DeliveryMapper;
import com.domain_expansion.integrity.delivery.domain.repository.DeliveryRepository;
import com.domain_expansion.integrity.delivery.domain.service.DeliveryDomainService;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryCreateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.response.DeliveryResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImplV1 implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryDomainService deliveryDomainService;
    private final DeliveryMapper deliveryMapper;

    @Override
    public DeliveryResponseDto createDelivery(DeliveryCreateRequestDto requestDto) {

        return DeliveryResponseDto.from(deliveryRepository.save(
                deliveryMapper.deliveryCreateRequestDtoAndDeliveryIdToDelivery(requestDto,
                        Ksuid.newKsuid().toString())));
    }

    @Override
    public void deleteDelivery(String OrderId) {

    }

    @Override
    public DeliveryResponseDto getDelivery(String requestDto) {
        return null;
    }

    @Override
    public Page<DeliveryResponseDto> getDeliveries(Pageable pageable) {
        return null;
    }

    @Override
    public DeliveryResponseDto updateDelivery(DeliveryUpdateRequestDto requestDto,
            String deliveryId) {

        return null;
    }
}

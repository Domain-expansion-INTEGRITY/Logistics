package com.domain_expansion.integrity.delivery.application.service;

import com.domain_expansion.integrity.delivery.application.mapper.DeliveryMapper;
import com.domain_expansion.integrity.delivery.common.exception.DeliveryException;
import com.domain_expansion.integrity.delivery.common.message.ExceptionMessage;
import com.domain_expansion.integrity.delivery.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import com.domain_expansion.integrity.delivery.domain.repository.DeliveryQueryRepository;
import com.domain_expansion.integrity.delivery.domain.repository.DeliveryRepository;
import com.domain_expansion.integrity.delivery.domain.service.DeliveryDomainService;
import com.domain_expansion.integrity.delivery.presentation.request.DeliverySearchCondition;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryCreateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.response.DeliveryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImplV1 implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryDomainService deliveryDomainService;
    private final DeliveryMapper deliveryMapper;
    private final DeliveryQueryRepository deliveryQueryRepository;

    @Override
    @KafkaListener(topics = "createdOrder", groupId = "a")
    public DeliveryResponseDto createDelivery(DeliveryCreateRequestDto requestDto) {

        String deliveryId = deliveryDomainService.createDeliveryId();
        
        return DeliveryResponseDto.from(deliveryRepository.save(
                deliveryMapper.toDelivery(requestDto, deliveryId)));
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryResponseDto getDelivery(String deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new DeliveryException(ExceptionMessage.NOT_FOUND_DELIVERY)
        );

        return DeliveryResponseDto.from(delivery);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryResponseDto> getDeliveries(
            Pageable pageable,
            DeliverySearchCondition searchCondition,
            UserDetailsImpl userDetails
    ) {

        return deliveryQueryRepository.findAllByCondition(pageable, searchCondition)
                .map(DeliveryResponseDto::from);
    }

    @Override
    public DeliveryResponseDto updateDelivery(
            DeliveryUpdateRequestDto requestDto,
            String deliveryId
    ) {

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new DeliveryException(ExceptionMessage.NOT_FOUND_DELIVERY)
        );

        delivery.updateDelivery(requestDto.address(), requestDto.receiver(), requestDto.receiverSlackId());

        return DeliveryResponseDto.from(delivery);
    }

    @Override
    public void deleteDelivery(String deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new DeliveryException(ExceptionMessage.NOT_FOUND_DELIVERY)
        );
        delivery.deleteDelivery();
    }

}

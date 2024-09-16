package com.domain_expansion.integrity.delivery.domain.service;

import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryDomainServiceImplV1 implements DeliveryDomainService {

    @Override
    public String createDeliveryId() {

        return Ksuid.newKsuid().toString();
    }
}

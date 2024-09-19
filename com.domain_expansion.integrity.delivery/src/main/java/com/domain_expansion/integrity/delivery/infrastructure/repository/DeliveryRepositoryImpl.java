package com.domain_expansion.integrity.delivery.infrastructure.repository;

import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import com.domain_expansion.integrity.delivery.domain.repository.DeliveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepositoryImpl extends JpaRepository<Delivery, String>,
        DeliveryRepository {

}

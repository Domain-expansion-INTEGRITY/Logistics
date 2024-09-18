package com.domain_expansion.integrity.hub.infrastructure.repository;

import com.domain_expansion.integrity.hub.domain.model.DeliveryMan;
import com.domain_expansion.integrity.hub.domain.repository.DeliveryManRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryManRepository extends JpaRepository<DeliveryMan, String> ,
        DeliveryManRepository {
}
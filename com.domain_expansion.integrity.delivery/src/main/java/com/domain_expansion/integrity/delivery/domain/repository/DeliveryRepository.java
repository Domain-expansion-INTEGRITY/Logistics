package com.domain_expansion.integrity.delivery.domain.repository;

import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository {

    Optional<Delivery> findById(String id);

    List<Delivery> findAll();

    Delivery save(Delivery delivery);
}

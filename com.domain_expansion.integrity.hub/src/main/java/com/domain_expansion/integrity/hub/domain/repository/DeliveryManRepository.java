package com.domain_expansion.integrity.hub.domain.repository;

import com.domain_expansion.integrity.hub.domain.model.DeliveryMan;
import java.util.List;
import java.util.Optional;

public interface DeliveryManRepository {

    Optional<DeliveryMan> findByDeliveryManId(String id);

    DeliveryMan save(DeliveryMan deliveryMan);

    Optional<DeliveryMan> findByUserId(Long userId);

    List<DeliveryMan> findAllByDeliveryManIdIn(List<String> deliveryManIds);

}

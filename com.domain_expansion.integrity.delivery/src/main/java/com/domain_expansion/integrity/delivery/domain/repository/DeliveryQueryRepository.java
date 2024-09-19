package com.domain_expansion.integrity.delivery.domain.repository;

import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import com.domain_expansion.integrity.delivery.presentation.request.DeliverySearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryQueryRepository {

    Page<Delivery> findAllByCondition(Pageable pageable, DeliverySearchCondition searchCondition);
}

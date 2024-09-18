package com.domain_expansion.integrity.order.domain.repository;

import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.presentation.request.OrderSearchCondition;
import com.netflix.appinfo.ApplicationInfoManager.InstanceStatusMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    Optional<Order> findById(String id);

    List<Order> findAll();

    Order save(Order product);
}

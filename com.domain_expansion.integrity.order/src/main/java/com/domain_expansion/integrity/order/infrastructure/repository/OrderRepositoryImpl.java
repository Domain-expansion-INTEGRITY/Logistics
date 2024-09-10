package com.domain_expansion.integrity.order.infrastructure.repository;

import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryImpl extends JpaRepository<Order, String>, OrderRepository {

}

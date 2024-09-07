package com.domain_expansion.integrity.product.infrastructure.repository;

import com.domain_expansion.integrity.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, String> {

}

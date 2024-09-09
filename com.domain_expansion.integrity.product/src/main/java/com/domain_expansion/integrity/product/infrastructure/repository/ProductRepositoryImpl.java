package com.domain_expansion.integrity.product.infrastructure.repository;

import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoryImpl extends JpaRepository<Product, String>, ProductRepository {

}

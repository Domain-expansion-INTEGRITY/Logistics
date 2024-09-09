package com.domain_expansion.integrity.product.domain.repository;

import com.domain_expansion.integrity.product.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(String id);

    List<Product> findAll();

    Product save(Product product);
}

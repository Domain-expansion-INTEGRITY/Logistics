package com.domain_expansion.integrity.product.domain.repository;

import com.domain_expansion.integrity.product.domain.model.Product;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(String id);

    Product save(Product product);

    void deleteById(String id);
}

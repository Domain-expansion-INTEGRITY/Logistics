package com.domain_expansion.integrity.product.domain.repository;

import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.presentation.request.ProductSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQueryRepository {

    Page<Product> findAllByCondition(Pageable pageable, ProductSearchCondition searchCondition);
}

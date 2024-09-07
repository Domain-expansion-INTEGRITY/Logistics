package com.domain_expansion.integrity.product.domain.service;

import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductDomainServiceImplV1 implements ProductDomainService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {

        return productRepository.save(product);
    }
}

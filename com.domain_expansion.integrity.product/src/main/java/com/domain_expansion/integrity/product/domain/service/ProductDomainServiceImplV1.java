package com.domain_expansion.integrity.product.domain.service;

import com.domain_expansion.integrity.product.domain.repository.ProductRepository;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductDomainServiceImplV1 implements ProductDomainService {

    private final ProductRepository productRepository;

    @Override
    public String createProductId() {
        return Ksuid.newKsuid().toString();
    }
}

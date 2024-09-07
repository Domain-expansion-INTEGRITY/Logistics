package com.domain_expansion.integrity.product.application.service;

import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.domain.service.ProductDomainService;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.domain_expansion.integrity.product.presentation.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImplV1 implements ProductService {

    private final ProductDomainService productDomainService;

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {

        return ProductResponseDto.from(productDomainService.save(Product.from(requestDto)));
    }
}

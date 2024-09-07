package com.domain_expansion.integrity.product.application.service;

import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.domain_expansion.integrity.product.presentation.response.ProductResponseDto;

public interface ProductService {

    ProductResponseDto createProduct(ProductCreateRequestDto requestDto);
}

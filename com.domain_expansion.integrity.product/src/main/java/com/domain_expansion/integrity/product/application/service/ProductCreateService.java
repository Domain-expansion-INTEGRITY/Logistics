package com.domain_expansion.integrity.product.application.service;

import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.domain_expansion.integrity.product.presentation.response.ProductResponseDto;

public interface ProductCreateService {

    ProductResponseDto createProduct(ProductCreateRequestDto requestDto,
            UserDetailsImpl userDetails);

}

package com.domain_expansion.integrity.product.application.service;

import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.product.presentation.request.ProductUpdateRequestDto;
import com.domain_expansion.integrity.product.presentation.response.ProductResponseDto;

public interface ProductUpdateService {

    ProductResponseDto updateProduct(ProductUpdateRequestDto requestDto, String productId,
            UserDetailsImpl userDetails);

}

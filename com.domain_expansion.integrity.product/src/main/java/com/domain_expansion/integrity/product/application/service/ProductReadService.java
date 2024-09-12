package com.domain_expansion.integrity.product.application.service;

import com.domain_expansion.integrity.product.presentation.request.ProductSearchCondition;
import com.domain_expansion.integrity.product.presentation.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReadService {

    ProductResponseDto getProduct(String productId);

    Page<ProductResponseDto> getProductsByCondition(Pageable pageable, ProductSearchCondition searchCondition);

}

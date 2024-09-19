package com.domain_expansion.integrity.product.application.service;

import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;

public interface ProductDeleteService {

    void deleteProduct(String productId, UserDetailsImpl userDetails);

}

package com.domain_expansion.integrity.product.presentation.controller;

import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_CREATE_PRODUCT;

import com.domain_expansion.integrity.product.application.service.ProductService;
import com.domain_expansion.integrity.product.common.response.CommonResponse;
import com.domain_expansion.integrity.product.common.response.SuccessResponse;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<? extends CommonResponse> createProduct(
            @Valid @RequestBody
            ProductCreateRequestDto requestDto
    ) {

        return ResponseEntity.status(SUCCESS_CREATE_PRODUCT.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_CREATE_PRODUCT.getMessage(), productService.createProduct(requestDto)));
    }
}

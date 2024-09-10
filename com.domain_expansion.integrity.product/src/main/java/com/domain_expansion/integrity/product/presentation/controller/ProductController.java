package com.domain_expansion.integrity.product.presentation.controller;

import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_CREATE_PRODUCT;
import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_GET_PRODUCT;
import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_GET_PRODUCTS;
import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_UPDATE_PRODUCT;
import static org.springframework.data.domain.Sort.Direction.*;

import com.domain_expansion.integrity.product.application.service.ProductService;
import com.domain_expansion.integrity.product.common.aop.DefaultPageSize;
import com.domain_expansion.integrity.product.common.response.CommonResponse;
import com.domain_expansion.integrity.product.common.response.SuccessResponse;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.domain_expansion.integrity.product.presentation.request.ProductUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{productId}")
    public ResponseEntity<? extends CommonResponse> getProduct(
            @PathVariable
            String productId
    ) {

        return ResponseEntity.status(SUCCESS_GET_PRODUCT.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_PRODUCT.getMessage(), productService.getProduct(productId)));
    }

    @GetMapping
    @DefaultPageSize
    public ResponseEntity<? extends CommonResponse> getProducts(
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC)
            Pageable pageable
    ) {

        return ResponseEntity.status(SUCCESS_GET_PRODUCTS.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_PRODUCTS.getMessage(), productService.getProducts(pageable)));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<? extends CommonResponse> updateProduct(
            @Valid @RequestBody
            ProductUpdateRequestDto requestDto,
            @PathVariable
            String productId
    ) {

        return ResponseEntity.status(SUCCESS_UPDATE_PRODUCT.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_UPDATE_PRODUCT.getMessage(), productService.updateProduct(requestDto, productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<? extends CommonResponse> deleteProduct(
            @PathVariable
            String productId
    ) {

        productService.deleteProduct(productId);

        return ResponseEntity.status(SUCCESS_CREATE_PRODUCT.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_CREATE_PRODUCT.getMessage()));
    }
}

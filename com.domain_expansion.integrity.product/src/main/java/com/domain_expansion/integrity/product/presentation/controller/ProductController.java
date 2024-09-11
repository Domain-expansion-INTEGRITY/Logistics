package com.domain_expansion.integrity.product.presentation.controller;

import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_CREATE_PRODUCT;
import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_GET_PRODUCT;
import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_GET_PRODUCTS;
import static com.domain_expansion.integrity.product.common.message.SuccessMessage.SUCCESS_UPDATE_PRODUCT;
import static com.domain_expansion.integrity.product.common.response.SuccessResponse.success;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.domain_expansion.integrity.product.application.service.ProductService;
import com.domain_expansion.integrity.product.common.aop.DefaultPageSize;
import com.domain_expansion.integrity.product.common.response.CommonResponse;
import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.domain_expansion.integrity.product.presentation.request.ProductSearchCondition;
import com.domain_expansion.integrity.product.presentation.request.ProductUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/products")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_COMPANY')")
    public ResponseEntity<? extends CommonResponse> createProduct(
            @Valid @RequestBody
            ProductCreateRequestDto requestDto,
            @AuthenticationPrincipal
            UserDetailsImpl userDetails
    ) {

        return ResponseEntity.status(SUCCESS_CREATE_PRODUCT.getHttpStatus())
                .body(success(SUCCESS_CREATE_PRODUCT.getMessage(),
                        productService.createProduct(requestDto, userDetails)));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<? extends CommonResponse> getProduct(
            @PathVariable
            String productId
    ) {

        return ResponseEntity.status(SUCCESS_GET_PRODUCT.getHttpStatus())
                .body(success(SUCCESS_GET_PRODUCT.getMessage(),
                        productService.getProduct(productId)));
    }

    @GetMapping
    @DefaultPageSize
    public ResponseEntity<? extends CommonResponse> getProductsByCondition(
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC)
            Pageable pageable,
            @ModelAttribute
            ProductSearchCondition searchCondition
    ) {

        return ResponseEntity.status(SUCCESS_GET_PRODUCTS.getHttpStatus())
                .body(success(SUCCESS_GET_PRODUCTS.getMessage(),
                        productService.getProductsByCondition(pageable, searchCondition)));
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HUB_COMPANY', 'ROLE_HUB_MANAGER')")
    public ResponseEntity<? extends CommonResponse> updateProduct(
            @Valid @RequestBody
            ProductUpdateRequestDto requestDto,
            @PathVariable
            String productId
    ) {

        return ResponseEntity.status(SUCCESS_UPDATE_PRODUCT.getHttpStatus())
                .body(success(SUCCESS_UPDATE_PRODUCT.getMessage(),
                        productService.updateProduct(requestDto, productId)));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HUB_COMPANY')")
    public ResponseEntity<? extends CommonResponse> deleteProduct(
            @PathVariable
            String productId
    ) {

        productService.deleteProduct(productId);

        return ResponseEntity.status(SUCCESS_CREATE_PRODUCT.getHttpStatus())
                .body(success(SUCCESS_CREATE_PRODUCT.getMessage()));
    }
}

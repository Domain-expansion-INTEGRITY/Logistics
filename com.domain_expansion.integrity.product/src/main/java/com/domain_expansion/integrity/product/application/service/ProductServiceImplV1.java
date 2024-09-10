package com.domain_expansion.integrity.product.application.service;

import com.domain_expansion.integrity.product.domain.mapper.ProductMapper;
import com.domain_expansion.integrity.product.domain.repository.ProductRepository;
import com.domain_expansion.integrity.product.domain.service.ProductDomainService;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.domain_expansion.integrity.product.presentation.request.ProductUpdateRequestDto;
import com.domain_expansion.integrity.product.presentation.response.ProductResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImplV1 implements ProductService {

    private final ProductDomainService productDomainService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {

        String productId = Ksuid.newKsuid().toString();

        return ProductResponseDto.from(
                productRepository.save(
                        productMapper.ProductCreateRequestDtoAndProductIdToProduct(
                                requestDto, productId
                        )
                )
        );
    }

    @Override
    public ProductResponseDto getProduct(String requestDto) {
        return null;
    }

    @Override
    public Page<ProductResponseDto> getProducts(Pageable pageable) {
        return null;
    }

    @Override
    public ProductResponseDto updateProduct(ProductUpdateRequestDto requestDto, String productId) {
        return null;
    }

    @Override
    public void deleteProduct(String productId) {

    }
}

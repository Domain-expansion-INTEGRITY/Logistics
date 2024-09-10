package com.domain_expansion.integrity.product.application.service;

import static com.domain_expansion.integrity.product.common.message.ExceptionMessage.NOT_FOUND_COMPANY;
import static com.domain_expansion.integrity.product.common.message.ExceptionMessage.NOT_FOUND_PRODUCT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.domain_expansion.integrity.product.application.client.CompanyClient;
import com.domain_expansion.integrity.product.application.client.response.CompanyResponseData;
import com.domain_expansion.integrity.product.common.exception.ProductException;
import com.domain_expansion.integrity.product.domain.mapper.ProductMapper;
import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.domain.model.info.CompanyInfo;
import com.domain_expansion.integrity.product.domain.repository.ProductQueryRepository;
import com.domain_expansion.integrity.product.domain.repository.ProductRepository;
import com.domain_expansion.integrity.product.domain.service.ProductDomainService;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.domain_expansion.integrity.product.presentation.request.ProductSearchCondition;
import com.domain_expansion.integrity.product.presentation.request.ProductUpdateRequestDto;
import com.domain_expansion.integrity.product.presentation.response.ProductResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImplV1 implements ProductService {

    private final ProductDomainService productDomainService;
    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;
    private final ProductMapper productMapper;
    private final CompanyClient companyClient;

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {

        ResponseEntity<CompanyResponseData> company = companyClient.getCompany(requestDto.companyId());
        CompanyInfo companyInfo = new CompanyInfo(company.getBody().getData().companyId(), company.getBody().getData().name());

        String productId = Ksuid.newKsuid().toString();

        return ProductResponseDto.from(
                productRepository.save(productMapper.toProduct(requestDto, productId, companyInfo))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(String productId) {

        return productRepository.findById(productId)
                .map(ProductResponseDto::from)
                .orElseThrow(() -> new ProductException(NOT_FOUND_PRODUCT));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProductsByCondition(Pageable pageable, ProductSearchCondition searchCondition) {

        return productQueryRepository.findAllByCondition(pageable, searchCondition)
                .map(ProductResponseDto::from);
    }

    @Override
    public ProductResponseDto updateProduct(ProductUpdateRequestDto requestDto, String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(NOT_FOUND_PRODUCT));

        product.updateProduct(requestDto);

        return ProductResponseDto.from(product);
    }

    @Override
    public void deleteProduct(String productId) {

        productRepository.deleteById(productId);
    }
}

package com.domain_expansion.integrity.product.application.service;

import static com.domain_expansion.integrity.product.common.message.ExceptionMessage.GUARD;
import static com.domain_expansion.integrity.product.common.message.ExceptionMessage.NOT_FOUND_PRODUCT;

import com.domain_expansion.integrity.product.application.client.CompanyClient;
import com.domain_expansion.integrity.product.application.client.HubClient;
import com.domain_expansion.integrity.product.application.client.response.CompanyResponseData;
import com.domain_expansion.integrity.product.application.client.response.ValidateUserResponseData;
import com.domain_expansion.integrity.product.common.exception.ProductException;
import com.domain_expansion.integrity.product.common.filter.UserRole;
import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
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
    private final HubClient hubClient;

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto,
            UserDetailsImpl userDetails) {

        validateUser(userDetails, requestDto.companyId());

        ResponseEntity<CompanyResponseData> company = companyClient.getCompany(
                requestDto.companyId());
        CompanyInfo companyInfo = new CompanyInfo(company.getBody().getData().companyId(),
                company.getBody().getData().name());

        String productId = Ksuid.newKsuid().toString();

        return ProductResponseDto.from(
                productRepository.save(productMapper.toProduct(requestDto, productId, companyInfo))
        );
    }

    private void validateUser(UserDetailsImpl userDetails, String companyId) {
        if (!userDetails.getRole().equals(UserRole.MASTER.getRole())) {

            if (userDetails.getRole().equals(UserRole.HUB_MANAGER.getRole())) {
                ResponseEntity<CompanyResponseData> companyResponse = companyClient.getCompany(companyId);
                String hubId = companyResponse.getBody().getData().hubId();
                ResponseEntity<ValidateUserResponseData> hubResponse = hubClient.validateUser(
                        userDetails.getUserId(), hubId);
                if (!hubResponse.getBody().getData().isOwner()) {
                    throw new ProductException(GUARD);
                }
            } else if (userDetails.getRole().equals(UserRole.HUB_COMPANY.getRole())) {
                ResponseEntity<ValidateUserResponseData> response = companyClient.validateUser(
                        userDetails.getUserId(), companyId);

                if (!response.getBody().getData().isOwner()) {
                    throw new ProductException(GUARD);
                }

            } else {
                throw new ProductException(GUARD);
            }
        }
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
    public Page<ProductResponseDto> getProductsByCondition(Pageable pageable,
            ProductSearchCondition searchCondition) {

        return productQueryRepository.findAllByCondition(pageable, searchCondition)
                .map(ProductResponseDto::from);
    }

    @Override
    public ProductResponseDto updateProduct(ProductUpdateRequestDto requestDto, String productId,
            UserDetailsImpl userDetails) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(NOT_FOUND_PRODUCT));

        validateUser(userDetails, product.getCompany().getCompanyId());

        product.updateProduct(requestDto);

        return ProductResponseDto.from(product);
    }

    @Override
    public void deleteProduct(String productId, UserDetailsImpl userDetails) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(NOT_FOUND_PRODUCT));

        validateUser(userDetails, product.getCompany().getCompanyId());

        productRepository.deleteById(productId);
    }
}

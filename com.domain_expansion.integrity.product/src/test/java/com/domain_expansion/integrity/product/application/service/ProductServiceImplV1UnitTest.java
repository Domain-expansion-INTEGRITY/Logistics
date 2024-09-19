package com.domain_expansion.integrity.product.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.domain_expansion.integrity.product.application.client.CompanyClient;
import com.domain_expansion.integrity.product.application.client.HubClient;
import com.domain_expansion.integrity.product.application.client.response.CompanyResponseData;
import com.domain_expansion.integrity.product.application.client.response.CompanyResponseDto;
import com.domain_expansion.integrity.product.application.client.response.ValidateUserData;
import com.domain_expansion.integrity.product.application.client.response.ValidateUserResponseData;
import com.domain_expansion.integrity.product.application.mock.UserDetailsImplFactory;
import com.domain_expansion.integrity.product.common.exception.ClientException;
import com.domain_expansion.integrity.product.common.exception.ProductException;
import com.domain_expansion.integrity.product.common.message.ExceptionMessage;
import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.product.domain.mapper.ProductMapper;
import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.domain.model.info.CompanyInfo;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductName;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductStock;
import com.domain_expansion.integrity.product.domain.repository.ProductQueryRepository;
import com.domain_expansion.integrity.product.domain.repository.ProductRepository;
import com.domain_expansion.integrity.product.domain.service.ProductDomainService;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.domain_expansion.integrity.product.presentation.request.ProductSearchCondition;
import com.domain_expansion.integrity.product.presentation.request.ProductUpdateRequestDto;
import com.domain_expansion.integrity.product.presentation.response.ProductResponseDto;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplV1UnitTest {

    @InjectMocks
    private ProductServiceImplV1 productService;

    @Mock
    private ProductDomainService productDomainService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductQueryRepository productQueryRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CompanyClient companyClient;

    @Mock
    private HubClient hubClient;

    @Nested
    @DisplayName("상품 생성 테스트 모음")
    class createProduct {

        @Test
        @DisplayName("상품 생성 성공 - MASTER")
        void createProduct_success() {
            // Given
            UserDetailsImpl masterUserDetails = UserDetailsImplFactory.createMasterUserDetails();
            ProductCreateRequestDto requestDto = new ProductCreateRequestDto("companyId", "상품이름",
                    1000);

            CompanyResponseDto companyResponseDto = mock(CompanyResponseDto.class);
            CompanyResponseData companyResponseData = mock(CompanyResponseData.class);

            when(companyClient.getCompany(requestDto.companyId())).thenReturn(
                    ResponseEntity.ok(companyResponseData));
            when(companyResponseData.getData()).thenReturn(companyResponseDto);
            when(companyResponseDto.companyId()).thenReturn("companyId");
            when(companyResponseDto.name()).thenReturn("CompanyName");

            String productId = "productId";
            when(productDomainService.createProductId()).thenReturn(productId);

            Product mockProduct = mock(Product.class);
            CompanyInfo mockCompanyInfo = mock(CompanyInfo.class);
            ProductName mockProductName = mock(ProductName.class);
            ProductStock mockProductStock = mock(ProductStock.class);

            when(productMapper.toProduct(eq(requestDto), eq(productId),
                    any(CompanyInfo.class))).thenReturn(mockProduct);
            when(mockProduct.getCompany()).thenReturn(mockCompanyInfo);
            when(mockProduct.getProductId()).thenReturn(productId);
            when(mockProduct.getName()).thenReturn(mockProductName);
            when(mockProductName.getValue()).thenReturn("ProductName");
            when(mockProduct.getStock()).thenReturn(mockProductStock);
            when(mockProductStock.getValue()).thenReturn(100);
            when(mockCompanyInfo.getCompanyId()).thenReturn("companyId");

            when(productRepository.save(any(Product.class)))
                    .thenReturn(mockProduct);

            // When
            ProductResponseDto result = productService.createProduct(requestDto, masterUserDetails);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.productName()).isEqualTo(mockProductName.getValue());
            assertThat(result.companyId()).isEqualTo(mockCompanyInfo.getCompanyId());
            assertThat(result.productId()).isEqualTo(productId);
            assertThat(result.companyName()).isEqualTo(mockCompanyInfo.getCompanyName());
            assertThat(result.stock()).isEqualTo(mockProductStock.getValue());
            verify(productRepository, times(1)).save(mockProduct);
        }

        @Test
        @DisplayName("상품 생성 실패 - 존재하지 않는 업체")
        void createProduct_fail_not_found_company() {
            // Given
            UserDetailsImpl masterUserDetails = UserDetailsImplFactory.createMasterUserDetails();
            ProductCreateRequestDto requestDto = new ProductCreateRequestDto("companyId", "상품이름",
                    1000);

            when(companyClient.getCompany(requestDto.companyId())).thenThrow(ClientException.class);

            // When + Then
            assertThatThrownBy(
                    () -> productService.createProduct(requestDto, masterUserDetails)).isInstanceOf(
                    ClientException.class);
        }

        @Test
        @DisplayName("상품 생성 실패 - 허브 매니저는 자신이 속하지 않은 허브(업체)에 상품을 생성할 수 없다.")
        void createProduct_fail_not_in_hub() {
            // Given
            UserDetailsImpl hubManagerUserDetails = UserDetailsImplFactory.createHubManagerUserDetails();
            ProductCreateRequestDto requestDto = new ProductCreateRequestDto("companyId", "상품이름",
                    1000);
            CompanyResponseDto companyResponseDto = mock(CompanyResponseDto.class);
            CompanyResponseData companyResponseData = mock(CompanyResponseData.class);
            String hubId = "hubId";
            when(companyClient.getCompany(requestDto.companyId())).thenReturn(
                    ResponseEntity.ok(companyResponseData));
            when(companyResponseData.getData()).thenReturn(companyResponseDto);
            when(companyResponseDto.hubId()).thenReturn(hubId);

            ValidateUserData validateUserData = mock(ValidateUserData.class);
            ValidateUserResponseData validateUserResponseData = mock(
                    ValidateUserResponseData.class);
            when(hubClient.validateUser(hubManagerUserDetails.getUserId(),
                    companyResponseDto.hubId())).thenReturn(
                    ResponseEntity.ok(validateUserResponseData));
            when(validateUserResponseData.getData()).thenReturn(validateUserData);
            when(validateUserData.isOwner()).thenReturn(false);

            // When + Then
            assertThatThrownBy(() -> productService.createProduct(requestDto,
                    hubManagerUserDetails)).isInstanceOf(
                            ProductException.class)
                    .hasMessage(ExceptionMessage.GUARD.getMessage());
        }

        @Test
        @DisplayName("상품 생성 실패 - 업체는 자신이 속하지 않은 업체에 상품을 생성할 수 없다.")
        void createProduct_fail_not_in_company() {
            // Given
            UserDetailsImpl hubCompanyUserDetails = UserDetailsImplFactory.createHubCompanyUserDetails();
            ProductCreateRequestDto requestDto = new ProductCreateRequestDto("companyId", "상품이름",
                    1000);
            ValidateUserData validateUserData = mock(ValidateUserData.class);
            ValidateUserResponseData validateUserResponseData = mock(
                    ValidateUserResponseData.class);
            when(companyClient.validateUser(hubCompanyUserDetails.getUserId(),
                    requestDto.companyId())).thenReturn(
                    ResponseEntity.ok(validateUserResponseData));
            when(validateUserResponseData.getData()).thenReturn(validateUserData);
            when(validateUserData.isOwner()).thenReturn(false);

            // When + Then
            assertThatThrownBy(() -> productService.createProduct(requestDto,
                    hubCompanyUserDetails)).isInstanceOf(
                            ProductException.class)
                    .hasMessage(ExceptionMessage.GUARD.getMessage());
        }


    }

    @Nested
    @DisplayName("상품 단 건 조회 테스트 모음")
    class getProduct {

        private Product product;

        private String productId = "productId";
        private String productNameValue = "productName";
        private ProductName productName = new ProductName(productNameValue);
        private Integer productStockValue = 100;
        private ProductStock productStock = new ProductStock(productStockValue);

        private String companyId = "companyId";
        private String companyName = "companyName";
        private CompanyInfo companyInfo = new CompanyInfo(companyId, companyName);

        @BeforeEach
        void setUp() {
            product = new Product(productId, productName, productStock, companyInfo);
        }

        @Test
        @DisplayName("상품 단 건 조회 성공")
        void getProduct_success() {
            // Given
            when(productRepository.findById(productId)).thenReturn(Optional.of(product));
            // When
            ProductResponseDto result = productService.getProduct(productId);
            // Then
            assertThat(result).isNotNull();
            assertThat(result.productId()).isEqualTo(productId);
            assertThat(result.productName()).isEqualTo(productNameValue);
            assertThat(result.stock()).isEqualTo(productStockValue);
            assertThat(result.companyId()).isEqualTo(companyId);
            assertThat(result.companyName()).isEqualTo(companyName);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 상품")
        void getProduct_fail_not_found_company() {
            // Given
            when(productRepository.findById(productId)).thenReturn(Optional.empty());
            // When + Then
            assertThatThrownBy(() -> productService.getProduct(productId))
                    .isInstanceOf(ProductException.class)
                    .hasMessage(ExceptionMessage.NOT_FOUND_PRODUCT.getMessage());
        }

    }


    @Nested
    @DisplayName("상품 목록 조회 테스트 모음")
    class getProducts {

        private Product product;

        private String productId = "productId";
        private String productNameValue = "productName";
        private ProductName productName = new ProductName(productNameValue);
        private Integer productStockValue = 100;
        private ProductStock productStock = new ProductStock(productStockValue);

        private String companyId = "companyId";
        private String companyName = "companyName";
        private CompanyInfo companyInfo = new CompanyInfo(companyId, companyName);

        private Pageable pageable;
        private ProductSearchCondition searchCondition;

        @BeforeEach
        void setUp() {
            product = new Product(productId, productName, productStock, companyInfo);

            pageable = PageRequest.of(0, 10);
            searchCondition = new ProductSearchCondition("productId", "ProductName");
        }


        @Test
        @DisplayName("성공 - 조건에 맞는 상품 리스트")
        void getProductsByCondition_success() {
            // Given
            Page<Product> productPage = new PageImpl<>(List.of(product));
            when(productQueryRepository.findAllByCondition(pageable, searchCondition))
                    .thenReturn(productPage);

            // When
            Page<ProductResponseDto> result = productService.getProductsByCondition(pageable,
                    searchCondition);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);  // 리스트에 1개의 상품이 있는지 확인
            assertThat(result.getContent().get(0).productId()).isEqualTo(productId);
            assertThat(result.getContent().get(0).productName()).isEqualTo(productName.getValue());
        }

        @Test
        @DisplayName("성공 - 조건에 맞는 상품이 없을 때 빈 리스트 반환")
        void getProductsByCondition_empty() {
            // Given
            Page<Product> emptyProductPage = new PageImpl<>(List.of());
            when(productQueryRepository.findAllByCondition(pageable, searchCondition))
                    .thenReturn(emptyProductPage);

            // When
            Page<ProductResponseDto> result = productService.getProductsByCondition(pageable, searchCondition);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).isEmpty();
        }
    }

    @Nested
    @DisplayName("상품 업데이트 테스트 모음")
    class updateProduct {

        private Product product;

        private String productId = "productId";
        private String productNameValue = "productName";
        private ProductName productName = new ProductName(productNameValue);
        private Integer productStockValue = 100;
        private ProductStock productStock = new ProductStock(productStockValue);

        private String companyId = "companyId";
        private String companyName = "companyName";
        private CompanyInfo companyInfo = new CompanyInfo(companyId, companyName);

        private ProductUpdateRequestDto requestDto;
        private String updatedProductName = "updatedProductName";

        @BeforeEach
        void setUp() {
            product = new Product(productId, productName, productStock, companyInfo);
            requestDto = new ProductUpdateRequestDto(updatedProductName);
        }

        @Test
        @DisplayName("성공 - 상품 업데이트 MASTER")
        void updateProduct_success() {
            // Given
            UserDetailsImpl userDetails = UserDetailsImplFactory.createMasterUserDetails();
            when(productRepository.findById(productId)).thenReturn(Optional.of(product));
            // When
            ProductResponseDto result = productService.updateProduct(requestDto,
                    productId, userDetails);
            // Then
            assertThat(result).isNotNull();
            assertThat(result.productName()).isEqualTo(updatedProductName);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 상품")
        void updateProduct_fail_not_found_product() {
            // Given
            UserDetailsImpl userDetails = UserDetailsImplFactory.createMasterUserDetails();
            when(productRepository.findById(productId)).thenReturn(Optional.empty());
            // When + Then
            assertThatThrownBy(() -> productService.updateProduct(requestDto, productId, userDetails))
                    .isInstanceOf(ProductException.class)
                    .hasMessage(ExceptionMessage.NOT_FOUND_PRODUCT.getMessage());
        }
    }


    @Nested
    @DisplayName("상품 삭제 테스트 코드 모음")
    class deleteProduct {
        private Product product;

        private String productId = "productId";
        private String productNameValue = "productName";
        private ProductName productName = new ProductName(productNameValue);
        private Integer productStockValue = 100;
        private ProductStock productStock = new ProductStock(productStockValue);

        private String companyId = "companyId";
        private String companyName = "companyName";
        private CompanyInfo companyInfo = new CompanyInfo(companyId, companyName);

        @BeforeEach
        void setUp() {
            product = new Product(productId, productName, productStock, companyInfo);
        }

        @Test
        @DisplayName("성공 - 정상적인 상품 삭제")
        void deleteProduct_success() {
            // Given
            UserDetailsImpl masterUserDetails = UserDetailsImplFactory.createMasterUserDetails();
            when(productRepository.findById(productId)).thenReturn(Optional.of(product));

            // When
            productService.deleteProduct(productId, masterUserDetails);

            // Then
            verify(productRepository, times(1)).findById("productId");
            verify(productRepository, times(1)).deleteById("productId");
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 상품 삭제")
        void deleteProduct_fail_not_found_product() {
            // Given
            UserDetailsImpl masterUserDetails = UserDetailsImplFactory.createMasterUserDetails();
            when(productRepository.findById(productId)).thenReturn(Optional.empty());

            // When + Then
            assertThatThrownBy(() -> productService.deleteProduct(productId, masterUserDetails))
                    .isInstanceOf(ProductException.class)
                    .hasMessage(ExceptionMessage.NOT_FOUND_PRODUCT.getMessage());
        }

    }

}
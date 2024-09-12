package com.domain_expansion.integrity.product.domain.mapper;

import com.domain_expansion.integrity.product.application.client.response.CompanyResponseData;
import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.domain.model.info.CompanyInfo;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductName;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductStock;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(
            ProductCreateRequestDto productCreateRequestDto,
            String productId,
            CompanyInfo companyInfo
    ) {

        return Product.from(
                productId,
                new ProductName(productCreateRequestDto.productName()),
                new ProductStock(productCreateRequestDto.stock()),
                companyInfo
        );
    }
}

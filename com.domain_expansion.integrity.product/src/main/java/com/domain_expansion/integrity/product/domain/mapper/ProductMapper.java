package com.domain_expansion.integrity.product.domain.mapper;

import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductName;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductStock;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product ProductCreateRequestDtoAndProductIdToProduct(
            ProductCreateRequestDto productCreateRequestDto, String productId
    ) {

        return Product.from(
                productId,
                new ProductName(productCreateRequestDto.productName()),
                new ProductStock(productCreateRequestDto.stock()),
                productCreateRequestDto.companyId()
        );
    }
}

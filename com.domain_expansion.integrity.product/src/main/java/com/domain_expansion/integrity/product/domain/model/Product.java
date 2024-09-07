package com.domain_expansion.integrity.product.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.product.domain.model.vo.product.ProductName;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductStock;
import com.domain_expansion.integrity.product.presentation.request.ProductCreateRequestDto;
import com.github.ksuid.Ksuid;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity @Table(name = "p_product")
@NoArgsConstructor(access = PROTECTED)
public class Product {

    @Id
    private String productId;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductStock stock;

    private String companyId; // 이걸 어떻게 할 지 고민해야해용

    @Builder(access = PRIVATE)
    public Product(String productId, ProductName name, ProductStock stock, String companyId) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.companyId = companyId;
    }

    public static Product from(ProductCreateRequestDto requestDto) {

        return Product.builder()
                .productId(Ksuid.newKsuid().toString()) // 이게 맞는 지는 잘 모르겠어용
                .name(new ProductName(requestDto.productName()))
                .stock(new ProductStock(requestDto.stock()))
                .companyId(requestDto.companyId())
                .build();
    }
}

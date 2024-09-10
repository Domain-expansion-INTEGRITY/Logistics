package com.domain_expansion.integrity.product.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.product.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductName;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductStock;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_product")
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseDateEntity {

    @Id
    private String productId;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductStock stock;

    @Column
    private String companyId; // 이걸 어떻게 할 지 고민해야해용

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<HubProduct> hubProducts;

    @Builder(access = PRIVATE)
    public Product(String productId, ProductName name, ProductStock stock, String companyId) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.companyId = companyId;
    }

    public static Product from(
            String productId, ProductName productName, ProductStock productStock, String companyId
    ) {

        return Product.builder()
                .productId(productId)
                .name(productName)
                .stock(productStock)
                .companyId(companyId)
                .build();
    }

    public void addHubProduct(HubProduct hubProduct) {

        hubProducts.add(hubProduct);
        hubProduct.setProduct(this);
    }
}

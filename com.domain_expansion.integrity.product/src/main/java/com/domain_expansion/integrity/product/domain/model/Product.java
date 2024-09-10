package com.domain_expansion.integrity.product.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.product.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.product.domain.model.info.CompanyInfo;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductName;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductStock;
import com.domain_expansion.integrity.product.presentation.request.ProductUpdateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "p_product")
@SQLRestriction("is_deleted IS FALSE")
@SQLDelete(sql = "UPDATE p_product SET deleted_at = CURRENT_TIMESTAMP, is_deleted = true WHERE product_id = ?")
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseDateEntity {

    @Id
    private String productId;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductStock stock;

    @Embedded
    private CompanyInfo company; // 이걸 어떻게 할 지 고민해야해용

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<HubProduct> hubProducts;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private Boolean isDeleted;

    @Builder(access = PRIVATE)
    public Product(String productId, ProductName name, ProductStock stock, CompanyInfo company) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.company = company;
        this.isDeleted = false;
    }

    public static Product from(
            String productId, ProductName productName, ProductStock productStock, CompanyInfo company
    ) {

        return Product.builder()
                .productId(productId)
                .name(productName)
                .stock(productStock)
                .company(company)
                .build();
    }

    public void addHubProduct(HubProduct hubProduct) {

        hubProducts.add(hubProduct);
        hubProduct.setProduct(this);
    }

    public void updateProduct(ProductUpdateRequestDto requestDto) {

        this.name = new ProductName(requestDto.productName());
    }
}

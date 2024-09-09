package com.domain_expansion.integrity.order.domain.model.info;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ProductInfo {

    @Column(name = "product_id")
    private String productId;

    // TODO 결합도 낮추기
    private String productName;

    public ProductInfo(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public ProductInfo() {

    }
}

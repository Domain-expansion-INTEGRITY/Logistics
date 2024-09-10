package com.domain_expansion.integrity.product.domain.model;

import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.product.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.product.domain.model.vo.product.ProductStock;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_hub_product")
@NoArgsConstructor(access = PROTECTED)
public class HubProduct extends BaseDateEntity {

    @Id
    private String hubProductId;

    @Column
    private String hubId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "stock", nullable = false)
    private ProductStock stock;

    @Column(nullable = false)
    private Boolean isDeleted;

    public void setProduct(Product product) {

        this.product = product;
    }
}

package com.domain_expansion.integrity.order.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.order.domain.model.info.ProductInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_order_product")
@NoArgsConstructor(access = PROTECTED)
public class OrderProduct {

    @Id
    @Column(name = "order_product_id")
    private String orderProductId;

    @Embedded
    private ProductInfo productInfo;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Integer count;

    @Builder(access = PRIVATE)
    public OrderProduct(String orderProductId, ProductInfo productInfo, Integer count) {
        this.orderProductId = orderProductId;
        this.productInfo = productInfo;
        this.count = count;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

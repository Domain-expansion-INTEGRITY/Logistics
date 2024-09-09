package com.domain_expansion.integrity.order.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.order.common.entity.BaseDateEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_order")
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseDateEntity {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(nullable = false)
    private Boolean isDelete;

    @Column
    private String sellerCompanyId;

    @Column
    private String buyerCompanyId;


    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @Builder(access = PRIVATE)
    public Order(String orderId, boolean isDelete, String sellerCompanyId, String buyerCompanyId) {
        this.orderId = orderId;
        this.isDelete = isDelete;
        this.sellerCompanyId = sellerCompanyId;
        this.buyerCompanyId = buyerCompanyId;
    }

    public static Order of(
            String orderId, Boolean isDelete, String sellerCompanyId, String buyerCompanyId
    ) {

        return Order.builder()
                    .orderId(orderId)
                    .isDelete(isDelete)
                    .sellerCompanyId(sellerCompanyId)
                    .buyerCompanyId(buyerCompanyId)
                    .build();
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }
}

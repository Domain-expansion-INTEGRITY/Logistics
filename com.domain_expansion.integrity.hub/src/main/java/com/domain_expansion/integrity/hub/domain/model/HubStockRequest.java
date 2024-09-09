package com.domain_expansion.integrity.hub.domain.model;


import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubStock;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_stock_request")
public class HubStockRequest {

    @Id
    @Column(name = "hub_stock_request_id")
    private String hubStockRequestId;

    @Embedded
    private HubStock stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockRequestStatus status;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "hub_id", nullable = false)
    private String hubId;

    public void setStatus(StockRequestStatus status) {
        this.status = status;
    }
}

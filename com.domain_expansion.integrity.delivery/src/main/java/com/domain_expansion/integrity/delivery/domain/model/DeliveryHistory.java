package com.domain_expansion.integrity.delivery.domain.model;

import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.delivery.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.delivery.domain.model.constant.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_delivery_history_id")
@NoArgsConstructor(access = PROTECTED)
public class DeliveryHistory extends BaseDateEntity {

    @Id
    private String deliveryHistoryId;

    @ManyToOne
    @JoinColumn(name = "delivery_id", updatable = false)
    private Delivery delivery;

    @Column(updatable = false)
    private String deliveryManId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private DeliveryStatus status;

    @Column(nullable = false, updatable = false)
    private Integer exDistance;

    @Column(nullable = false, updatable = false)
    private Integer exDuration;

    @Column
    private Integer distance;

    @Column
    private Integer duration;

    @Column(nullable = false)
    private Integer isDelete;
}

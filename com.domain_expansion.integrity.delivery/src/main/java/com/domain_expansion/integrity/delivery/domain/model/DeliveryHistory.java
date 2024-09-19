package com.domain_expansion.integrity.delivery.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.delivery.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.delivery.domain.model.constant.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_delivery_history_id")
@NoArgsConstructor(access = PROTECTED)
public class DeliveryHistory extends BaseDateEntity {

    @Id
    private String deliveryHistoryId;

    @OneToOne
    @JoinColumn(name = "delivery_id")
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
    private Boolean isDelete;

    @Builder(access = PRIVATE)
    public DeliveryHistory(String deliveryHistoryId, String deliveryManId, DeliveryStatus status,
            Integer exDistance, Integer exDuration, Boolean isDelete) {
        this.deliveryHistoryId = deliveryHistoryId;
        this.deliveryManId = deliveryManId;
        this.status = status;
        this.exDistance = exDistance;
        this.exDuration = exDuration;
        this.isDelete = isDelete;
    }

    public static DeliveryHistory of(Integer exDistance, Integer exDuration, Boolean isDelete, String deliveryManId, String deliveryHistoryId) {

        return DeliveryHistory.builder()
                .exDistance(exDistance)
                .exDuration(exDuration)
                .isDelete(isDelete)
                .status(DeliveryStatus.DELIVERING_FOR_COMPANY)
                .deliveryManId(deliveryManId)
                .deliveryHistoryId(deliveryHistoryId)
                .build();
    }

    public void setDelivery(Delivery delivery) {

        this.delivery = delivery;
    }
}

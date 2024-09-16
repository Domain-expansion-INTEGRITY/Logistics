package com.domain_expansion.integrity.delivery.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.delivery.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.delivery.domain.model.constant.DeliveryStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "p_delivery")
@NoArgsConstructor(access = PROTECTED)
public class Delivery extends BaseDateEntity {

    @Id
    @Column(name = "delivery_id")
    private String deliveryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column
    private String deliveryManId;

    @Column
    private String orderId;

    @Column
    private String startHubId;

    @Column
    private String endHubId;

    @Column
    private String address;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
    private String receiverSlackId;

    @Column(nullable = false)
    private Boolean isDelete;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.PERSIST)
    private Set<DeliveryHistory> deliveryHistories = new HashSet<>();

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.PERSIST)
    private Set<HubDeliveryHistory> hubDeliveryHistories = new HashSet<>();

    @Builder(access = PRIVATE)
    public Delivery(String deliveryId, DeliveryStatus status, String deliveryManId, String orderId,
            String startHubId, String endHubId, String address, String receiver, String receiverSlackId,
            Boolean isDelete) {
        this.deliveryId = deliveryId;
        this.status = status;
        this.deliveryManId = deliveryManId;
        this.orderId = orderId;
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.address = address;
        this.receiver = receiver;
        this.receiverSlackId = receiverSlackId;
        this.isDelete = isDelete;
    }

    public static Delivery of(String deliveryId, DeliveryStatus status, String deliveryManId,
            String orderId, String startHubId, String endHubId, String address, String receiver,
            String receiverSlackId, Boolean isDelete) {

        return Delivery.builder()
                .deliveryId(deliveryId)
                .status(status)
                .deliveryManId(deliveryManId)
                .orderId(orderId)
                .startHubId(startHubId)
                .endHubId(endHubId)
                .receiver(receiver)
                .address(address)
                .receiverSlackId(receiverSlackId)
                .isDelete(isDelete)
                .build();
    }

    public void updateDelivery(String address, String receiver, String receiverSlackId) {

        this.address = address;
        this.receiver = receiver;
        this.receiverSlackId = receiverSlackId;
    }

    public void deleteDelivery() {
        this.isDelete = true;
        super.deleteEntity();
    }
}

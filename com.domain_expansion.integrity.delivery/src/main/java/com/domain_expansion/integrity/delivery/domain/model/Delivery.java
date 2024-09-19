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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "p_delivery")
@NoArgsConstructor(access = PROTECTED)
@SQLRestriction("is_delete = false")
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
    private String hubDeliveryManId;

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

    @OneToOne(mappedBy = "delivery", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DeliveryHistory deliveryHistory;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.PERSIST)
    private Set<HubDeliveryHistory> hubDeliveryHistories = new HashSet<>();

    @Builder(access = PRIVATE)
    public Delivery(String deliveryId, DeliveryStatus status, String orderId,
            String startHubId, String endHubId, String address, String receiver, String receiverSlackId,
            Boolean isDelete) {
        this.deliveryId = deliveryId;
        this.status = status;
        this.orderId = orderId;
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.address = address;
        this.receiver = receiver;
        this.receiverSlackId = receiverSlackId;
        this.isDelete = isDelete;
    }

    public static Delivery of(String deliveryId, DeliveryStatus status, String orderId,
            String startHubId, String endHubId, String address, String receiver,
            String receiverSlackId, Boolean isDelete) {

        return Delivery.builder()
                .deliveryId(deliveryId)
                .status(status)
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

    public void updateDeliveryMan(String deliveryManId) {

        this.deliveryManId = deliveryManId;
    }

    public void updateHubDeliveryMan(String hubDeliveryManId) {

        this.hubDeliveryManId = hubDeliveryManId;
    }

    public void updateDeliveryHistory(DeliveryHistory deliveryHistory) {

        this.deliveryHistory = deliveryHistory;
        deliveryHistory.setDelivery(this);
    }

    public void updateStatus(DeliveryStatus deliveryStatus) {

        this.status = deliveryStatus;
    }
}

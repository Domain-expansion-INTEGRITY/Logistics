package com.domain_expansion.integrity.hub.domain.model;

import com.domain_expansion.integrity.hub.common.entity.BaseDateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_delivery_man")
public class HubDeliveryMan extends BaseDateEntity {

    @Id
    @Column(name = "hub_delivery_man_id")
    private String hubDeliveryManId;

    @ManyToOne
    @JoinColumn(name = "hub_id",nullable = false)
    private Hub hub;

    @OneToOne
    @JoinColumn(name = "delivery_man_id",nullable = false)
    private DeliveryMan deliveryMan;

    @Builder(access = AccessLevel.PRIVATE)
    public HubDeliveryMan(String hubDeliveryManId, Hub hub, DeliveryMan deliveryMan) {
        this.hubDeliveryManId = hubDeliveryManId;
        this.hub = hub;
        this.deliveryMan = deliveryMan;
    }

    public static HubDeliveryMan from(String hubDeliveryManId,Hub hub, DeliveryMan deliveryMan)
    {
        return HubDeliveryMan.builder()
                .hubDeliveryManId(hubDeliveryManId)
                .hub(hub)
                .deliveryMan(deliveryMan)
                .build();
    }
}

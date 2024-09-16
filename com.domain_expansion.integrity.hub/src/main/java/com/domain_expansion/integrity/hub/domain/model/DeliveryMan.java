package com.domain_expansion.integrity.hub.domain.model;

import com.domain_expansion.integrity.hub.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_delivery_man")
@SQLRestriction("is_delete = false")
public class DeliveryMan extends BaseDateEntity {

    @Id
    @Column(name = "delivery_man_id")
    private String deliveryManId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Builder(access = AccessLevel.PRIVATE)
    public DeliveryMan(String deliveryManId, Long userId) {
        this.deliveryManId = deliveryManId;
        this.userId = userId;
    }

    public static DeliveryMan from(String deliveryManId, Long userId) {
        return DeliveryMan.builder()
                .deliveryManId(deliveryManId)
                .userId(userId)
                .build();
    }

    public void updateWith(DeliveryManUpdateRequestDto requestDto)
    {
        if(requestDto.userId() != null)
        {
            this.userId = requestDto.userId().longValue();
        }
    }

    public void deleteDeliveryMan(Long userId){
        super.setDeletedValue(userId);
        this.isDelete = true;
    }
}

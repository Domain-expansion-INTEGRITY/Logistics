package com.domain_expansion.integrity.hub.domain.model;

import com.domain_expansion.integrity.hub.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_delivery_man")
public class DeliveryMan extends BaseEntity {

    @Id
    @Column(name = "delivery_man_id")
    private String deliveryManId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_delete")
    @ColumnDefault(value = "false")
    private Boolean isDelete;

}

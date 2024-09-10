package com.domain_expansion.integrity.hub.domain.model;

import com.domain_expansion.integrity.hub.common.entity.BaseEntity;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLatitude;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLongitude;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "p_hub")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseEntity {

    @Id
    @Column(name = "hub_id")
    private String hubId;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Embedded
    private HubLatitude hubLatitude;

    @Embedded
    private HubLongitude hubLongitude;

    @Column(name = "is_delete")
    @ColumnDefault(value = "false")
    private Boolean isDelete;

    @Builder(access = AccessLevel.PRIVATE)
    public Hub(String hubId, Long userId, String name,String address,HubLatitude hubLatitude,HubLongitude hubLongitude) {
        this.hubId = hubId;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.hubLatitude = hubLatitude;
        this.hubLongitude = hubLongitude;
    }

    public static Hub from(
            String hubId, Long userId, String name,String address,HubLatitude hubLatitude,HubLongitude hubLongitude)
    {
        return Hub.builder()
                .hubId(hubId)
                .userId(userId)
                .name(name)
                .address(address)
                .hubLatitude(hubLatitude)
                .hubLongitude(hubLongitude)
                .build();
    }
}

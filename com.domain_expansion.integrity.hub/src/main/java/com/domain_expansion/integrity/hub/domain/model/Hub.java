package com.domain_expansion.integrity.hub.domain.model;

import com.domain_expansion.integrity.hub.common.entity.BaseEntity;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubAddress;
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

    @Column(name = "user_id")
    private Long userId;

    private String name;

    @Embedded
    private HubAddress hubAddress;

    @Embedded
    private HubLatitude hubLatitude;

    @Embedded
    private HubLongitude hubLongitude;

    @Column(name = "is_deleted")
    @ColumnDefault(value = "false")
    private Boolean isDeleted;

    @Builder(access = AccessLevel.PRIVATE)
    public Hub(String hubId, Long userId, String name,HubAddress hubAddress,HubLatitude hubLatitude,HubLongitude hubLongitude) {
        this.hubId = hubId;
        this.userId = userId;
        this.name = name;
        this.hubAddress = hubAddress;
        this.hubLatitude = hubLatitude;
        this.hubLongitude = hubLongitude;
    }

    public static Hub from(
            String hubId, Long userId, String name,HubAddress hubAddress,HubLatitude hubLatitude,HubLongitude hubLongitude)
    {
        return Hub.builder()
                .hubId(hubId)
                .userId(userId)
                .name(name)
                .hubAddress(hubAddress)
                .hubLatitude(hubLatitude)
                .hubLongitude(hubLongitude)
                .build();
    }
}

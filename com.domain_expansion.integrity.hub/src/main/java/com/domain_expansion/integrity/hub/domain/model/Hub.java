package com.domain_expansion.integrity.hub.domain.model;

import com.domain_expansion.integrity.hub.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLatitude;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLongitude;
import com.domain_expansion.integrity.hub.presentation.request.HubUpdateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "p_hub")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_delete = false")
public class Hub extends BaseDateEntity {

    @Id
    @Column(name = "hub_id")
    private String hubId;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double index; //허브간 이동순서를 정의

    @Embedded
    private HubLatitude hubLatitude;

    @Embedded
    private HubLongitude hubLongitude;

    @OneToMany(mappedBy = "hub",cascade = CascadeType.ALL ,orphanRemoval = true)
    @BatchSize(size = 10)//업체 배송담당자는 10명이기 때문에 설정
    private List<HubDeliveryMan> deliveryMans = new ArrayList<>();

    @OneToMany(mappedBy = "startHub", cascade = CascadeType.ALL)
    private List<HubRoute> startRoutes = new ArrayList<>();

    @OneToMany(mappedBy = "endHub", cascade = CascadeType.ALL)
    private List<HubRoute> endRoutes = new ArrayList<>();

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Builder(access = AccessLevel.PRIVATE)
    public Hub(String hubId, Long userId, String name,String address,double index,HubLatitude hubLatitude,HubLongitude hubLongitude) {
        this.hubId = hubId;
        this.userId = userId;
        this.name = name;
        this.index = index;
        this.address = address;
        this.hubLatitude = hubLatitude;
        this.hubLongitude = hubLongitude;
    }

    public static Hub from(
            String hubId, Long userId, String name,String address,double index,HubLatitude hubLatitude,HubLongitude hubLongitude)
    {
        return Hub.builder()
                .hubId(hubId)
                .userId(userId)
                .name(name)
                .address(address)
                .index(index)
                .hubLatitude(hubLatitude)
                .hubLongitude(hubLongitude)
                .build();
    }

    public void deleteHub(Long userId)
    {
        super.setDeletedValue(userId);
        isDelete = true;
    }

    public void updateWith(HubUpdateRequestDto requestDto) {

        if(requestDto.userId() != null)
        {
            this.userId = requestDto.userId();
        }

        if(requestDto.name() != null)
        {
            this.name = requestDto.name();
        }

        if(requestDto.address() != null)
        {
            this.address = requestDto.address();
        }

        if(requestDto.latitude() != null)
        {
            this.hubLatitude = new HubLatitude(requestDto.latitude());
        }

        if(requestDto.longitude() != null)
        {
            this.hubLongitude = new HubLongitude(requestDto.longitude());
        }

    }
}

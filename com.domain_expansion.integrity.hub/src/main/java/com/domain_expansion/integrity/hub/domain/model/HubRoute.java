package com.domain_expansion.integrity.hub.domain.model;

import com.domain_expansion.integrity.hub.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.hub.presentation.request.HubRouteUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_route")
public class HubRoute extends BaseDateEntity {

    @Id
    @Column(name = "hub_route_id")
    private String hubRouteId;

    @ManyToOne
    @JoinColumn(name = "start_hub_id", nullable = false)
    private Hub startHub;

    @ManyToOne
    @JoinColumn(name = "end_hub_id", nullable = false)
    private Hub endHub;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer distance;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Builder(access = AccessLevel.PRIVATE)
    public HubRoute(String hubRouteId, Hub startHub, Hub endHub, Integer duration, Integer distance) {
        this.hubRouteId = hubRouteId;
        this.startHub = startHub;
        this.endHub = endHub;
        this.duration = duration;
        this.distance = distance;
    }

    public static HubRoute from(String hubRouteId, Hub startHub, Hub endHub, Integer duration,Integer distance) {
        return HubRoute.builder()
                .hubRouteId(hubRouteId)
                .startHub(startHub)
                .endHub(endHub)
                .duration(duration)
                .distance(distance)
                .build();
    }

    public void deleteHubRoute(Long userId){
        super.setDeletedValue(userId);
        this.isDelete = true;
    }

    public void updateHubRoute(HubRouteUpdateRequestDto requestDto){

        if(requestDto.distance() != null)
        {
            this.distance = requestDto.distance();
        }

        if(requestDto.duration() != null)
        {
            this.duration = requestDto.duration();
        }
    }

}

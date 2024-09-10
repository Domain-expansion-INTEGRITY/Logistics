package com.domain_expansion.integrity.hub.domain.model.vo.hub;

import com.domain_expansion.integrity.hub.common.exception.HubException;
import com.domain_expansion.integrity.hub.common.message.ExceptionMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubLongitude {

    @Column(nullable = false)
    private double longitude;

    public HubLongitude(double longitude) {

        if(longitude < -180 || longitude > 180) {
            throw new HubException(ExceptionMessage.LONGITUDE_MUST_BETWEEN_180_MINUS_180);
        }

        this.longitude = longitude;
    }


}

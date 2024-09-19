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
public class HubLatitude {

    @Column(nullable = false)
    private double latitude;

    public HubLatitude(double latitude) {

        if(latitude < -180 || latitude > 180) {
            throw new HubException(ExceptionMessage.LATITUDE_MUST_BETWEEN_90_MINUS_90);
        }

        this.latitude = latitude;
    }

}

package com.domain_expansion.integrity.hub.domain.model.vo.hub;

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
public class HubAddress {

    private String address;

    public HubAddress(String address) {
        this.address = address;
    }
}

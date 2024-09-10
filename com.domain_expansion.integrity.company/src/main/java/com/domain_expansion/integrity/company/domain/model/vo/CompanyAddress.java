package com.domain_expansion.integrity.company.domain.model.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyAddress {

    private String address;

    public CompanyAddress(String address) {
        this.address = address;
    }
}

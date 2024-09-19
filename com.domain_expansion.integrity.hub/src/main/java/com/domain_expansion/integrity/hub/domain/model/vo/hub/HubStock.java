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
public class HubStock {

    @Column(nullable = false,name = "stock")
    private Integer value;

    public HubStock(Integer value) {

        if(value <= 0 ) {
            throw new HubException(ExceptionMessage.STOCK_MUST_NOT_MINUS);
        }

        this.value = value;
    }
}

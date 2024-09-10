package com.domain_expansion.integrity.product.domain.model.vo.product;

import com.domain_expansion.integrity.product.common.exception.ProductException;
import com.domain_expansion.integrity.product.common.message.ExceptionMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Embeddable
@ToString @EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductStock {

    @Column(name = "stock", nullable = false)
    private Integer value;

    public ProductStock(Integer value) {

        if (value == null || value < 0) {
            throw new ProductException(ExceptionMessage.STOCK_MUST_HAVE_VALUE);
        }

        this.value = value;
    }
}

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
public class ProductName {

    @Column(name = "name", nullable = false)
    private String value;

    public ProductName(String value) {

        if (value == null) {
            throw new ProductException(ExceptionMessage.PRODUCT_NAME_MIN_VALUE);
        }

        value = value.trim();

        if (value.isEmpty()) {
            throw new ProductException(ExceptionMessage.PRODUCT_NAME_MIN_VALUE);
        }

        if (value.length() > 255) {
            throw new ProductException(ExceptionMessage.PRODUCT_NAME_MAX_VALUE);
        }

        this.value = value;
    }
}

package com.domain_expansion.integrity.product.domain.model.vo.product.converter;

import com.domain_expansion.integrity.product.domain.model.vo.product.ProductName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProductNameConverter implements AttributeConverter<ProductName, String> {

    @Override
    public String convertToDatabaseColumn(ProductName productName) {

        if (productName == null) {

            return null;
        }

        return productName.getValue();
    }

    @Override
    public ProductName convertToEntityAttribute(String dbData) {

        if (dbData == null || dbData.isEmpty()) {

            return null;
        }

        return new ProductName(dbData);
    }
}

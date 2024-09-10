package com.domain_expansion.integrity.product.domain.model.vo.product.converter;

import com.domain_expansion.integrity.product.domain.model.vo.product.ProductStock;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProductStockConverter implements AttributeConverter<ProductStock, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductStock productStock) {

        if (productStock == null) {

            return null;
        }

        return productStock.getValue();
    }

    @Override
    public ProductStock convertToEntityAttribute(Integer dbData) {

        if (dbData == null) {

            return null;
        }

        return new ProductStock(dbData);
    }
}

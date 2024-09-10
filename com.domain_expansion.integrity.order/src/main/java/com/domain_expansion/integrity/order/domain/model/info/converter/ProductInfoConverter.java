package com.domain_expansion.integrity.order.domain.model.info.converter;

import com.domain_expansion.integrity.order.domain.model.info.ProductInfo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProductInfoConverter implements AttributeConverter<ProductInfo, String> {

    @Override
    public String convertToDatabaseColumn(ProductInfo productInfo) {

        if (productInfo == null) {
            return null;
        }

        return productInfo.getProductId();
    }

    @Override
    public ProductInfo convertToEntityAttribute(String dbData) {

        if (dbData == null) {
            return null;
        }

        return new ProductInfo(dbData, null);
    }
}

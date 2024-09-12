package com.domain_expansion.integrity.hub.domain.model.vo.hub.converter;

import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubStock;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HubStockConverter implements AttributeConverter<HubStock, Integer> {

    @Override
    public Integer convertToDatabaseColumn(HubStock hubStock) {
        return hubStock.getValue();
    }

    @Override
    public HubStock convertToEntityAttribute(Integer integer) {
        return new HubStock(integer);
    }
}


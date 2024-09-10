package com.domain_expansion.integrity.hub.domain.model.vo.hub.converter;

import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLatitude;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HubLatitueConverter implements AttributeConverter<HubLatitude, Double> {

    @Override
    public Double convertToDatabaseColumn(HubLatitude latitude) {

        return latitude.getLatitude();
    }

    @Override
    public HubLatitude convertToEntityAttribute(Double dbData) {

        return new HubLatitude(dbData);
    }
}

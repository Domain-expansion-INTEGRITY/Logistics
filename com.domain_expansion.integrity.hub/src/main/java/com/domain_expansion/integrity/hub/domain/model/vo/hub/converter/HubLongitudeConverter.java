package com.domain_expansion.integrity.hub.domain.model.vo.hub.converter;

import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLatitude;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLongitude;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HubLongitudeConverter implements AttributeConverter<HubLongitude, Double> {

    @Override
    public Double convertToDatabaseColumn(HubLongitude longitude) {

        return longitude.getLongitude();
    }

    @Override
    public HubLongitude convertToEntityAttribute(Double dbData) {

        return new HubLongitude(dbData);
    }
}

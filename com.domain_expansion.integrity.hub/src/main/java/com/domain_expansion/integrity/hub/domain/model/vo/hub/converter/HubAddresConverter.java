package com.domain_expansion.integrity.hub.domain.model.vo.hub.converter;

import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HubAddresConverter implements AttributeConverter<HubAddress, String> {

    @Override
    public String convertToDatabaseColumn(HubAddress address) {

        if (address == null) {

            return null;
        }

        return address.toString();
    }

    @Override
    public HubAddress convertToEntityAttribute(String dbData) {

        if (dbData == null || dbData.isEmpty()) {

            return null;
        }

        return new HubAddress(dbData);
    }
}

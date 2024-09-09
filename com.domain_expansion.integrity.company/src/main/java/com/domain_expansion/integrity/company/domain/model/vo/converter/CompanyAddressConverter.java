package com.domain_expansion.integrity.company.domain.model.vo.converter;

import com.domain_expansion.integrity.company.domain.model.vo.CompanyAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CompanyAddressConverter implements AttributeConverter<CompanyAddress, String> {

    @Override
    public String convertToDatabaseColumn(CompanyAddress companyAddress) {

        if(companyAddress == null)
        {
            return null;
        }

        return companyAddress.getAddress();
    }

    @Override
    public CompanyAddress convertToEntityAttribute(String dbData) {

        if (dbData == null || dbData.isEmpty())
        {
            return null;
        }

        return new CompanyAddress(dbData);
    }
}

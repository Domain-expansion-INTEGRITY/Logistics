package com.domain_expansion.integrity.product.domain.model.info;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CompanyInfoConverter implements AttributeConverter<CompanyInfo, String> {

    @Override
    public String convertToDatabaseColumn(CompanyInfo companyInfo) {

        if (companyInfo == null) {

            return null;
        }

        return companyInfo.getCompanyId();
    }

    @Override
    public CompanyInfo convertToEntityAttribute(String dbData) {

        if (dbData == null) {

            return null;
        }

        return new CompanyInfo(dbData, null);
    }
}

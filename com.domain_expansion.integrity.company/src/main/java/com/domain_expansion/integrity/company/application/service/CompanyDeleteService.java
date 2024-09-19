package com.domain_expansion.integrity.company.application.service;

import com.domain_expansion.integrity.company.common.security.UserDetailsImpl;

public interface CompanyDeleteService {

    void deleteCompany(String companyId, UserDetailsImpl userDetails);

}

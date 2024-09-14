package com.domain_expansion.integrity.company.infrastructure.repository;


import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.repository.CompanyRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<Company,String>, CompanyRepository{

    List<Company> findAllByHubId(String hubId);

}

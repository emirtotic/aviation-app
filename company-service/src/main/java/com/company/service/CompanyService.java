package com.company.service;

import com.company.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> findAllCompanies();
    List<CompanyDTO> findAllCompaniesPerCountry(String country);
    CompanyDTO findCompanyByIata(String iata);
    CompanyDTO findCompanyByName(String name);
    CompanyDTO findCompanyById(Long id);
}

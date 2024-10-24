package com.company.service.impl;

import com.company.dto.CompanyDTO;
import com.company.mapper.CompanyMapper;
import com.company.repository.CompanyRepository;
import com.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyDTO> findAllCompanies() {
        return companyMapper.mapToDTO(companyRepository.findAll());
    }

    @Override
    public List<CompanyDTO> findAllCompaniesPerCountry(String country) {
        return companyMapper.mapToDTO(companyRepository.findAllByCountry(country));
    }

    @Override
    public CompanyDTO findCompanyByIata(String iata) {
        return companyMapper.mapToDTO(companyRepository.findCompanyByIataCode(iata));
    }

    @Override
    public CompanyDTO findCompanyByName(String name) {
        return companyMapper.mapToDTO(companyRepository.findCompanyByName(name));
    }

    @Override
    public CompanyDTO findCompanyById(Long id) {
        return companyMapper.mapToDTO(companyRepository.findById(id).orElseThrow()); // TODO: custom exception
    }
}

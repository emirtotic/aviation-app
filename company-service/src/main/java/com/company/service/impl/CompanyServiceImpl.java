package com.company.service.impl;

import com.company.dto.CompanyDTO;
import com.company.entity.Company;
import com.company.exception.CompanyNotFoundException;
import com.company.mapper.CompanyMapper;
import com.company.repository.CompanyRepository;
import com.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Company companyByIataCode = Optional.ofNullable(companyRepository.findCompanyByIataCode(iata))
                .orElseThrow(() -> new CompanyNotFoundException(iata));

        return companyMapper.mapToDTO(companyByIataCode);
    }

    @Override
    public CompanyDTO findCompanyByName(String name) {

        Company companyByName = Optional.ofNullable(companyRepository.findCompanyByName(name))
                .orElseThrow(() -> new CompanyNotFoundException(name));

        return companyMapper.mapToDTO(companyByName);
    }

    @Override
    public CompanyDTO findCompanyById(Long id) {
        return companyMapper.mapToDTO(companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id)));
    }
}

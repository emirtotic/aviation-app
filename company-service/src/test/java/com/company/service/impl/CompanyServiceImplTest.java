package com.company.service.impl;

import com.company.dto.CompanyDTO;
import com.company.entity.Company;
import com.company.exception.CompanyNotFoundException;
import com.company.mapper.CompanyMapper;
import com.company.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    private CompanyRepository companyRepository;

    private CompanyMapper companyMapper;

    private CompanyServiceImpl companyService;

    private Company company;
    private CompanyDTO companyDTO;

    @BeforeEach
    void setUp() {

        companyRepository = mock(CompanyRepository.class);
        companyMapper = mock(CompanyMapper.class);
        companyService = new CompanyServiceImpl(companyRepository, companyMapper);

        company = Company.builder()
                .id(1L)
                .name("Airline Co.")
                .yearFounded(1995)
                .country("USA")
                .iataCode("AC")
                .icaoCode("ACO")
                .fleetSize(100)
                .headquarters("New York")
                .destinations(50)
                .numberOfEmployees(5000)
                .website("http://www.airlineco.com")
                .contactInfo("contact@airlineco.com")
                .build();

        companyDTO = CompanyDTO.builder()
                .id(1L)
                .name("Airline Co.")
                .yearFounded(1995)
                .country("USA")
                .iataCode("AC")
                .icaoCode("ACO")
                .fleetSize(100)
                .headquarters("New York")
                .destinations(50)
                .numberOfEmployees(5000)
                .website("http://www.airlineco.com")
                .contactInfo("contact@airlineco.com")
                .build();
    }

    @Test
    @DisplayName("Find All Companies Test")
    void findAllCompanies_ShouldReturnListOfCompanyDTO() {
        when(companyRepository.findAll()).thenReturn(Collections.singletonList(company));
        when(companyMapper.mapToDTO(anyList())).thenReturn(Collections.singletonList(companyDTO));

        List<CompanyDTO> result = companyService.findAllCompanies();

        assertEquals(1, result.size());
        assertEquals(companyDTO, result.get(0));
        verify(companyRepository, times(1)).findAll();
        verify(companyMapper, times(1)).mapToDTO(anyList());
    }

    @Test
    @DisplayName("Find All Companies For Country Test")
    void findAllCompaniesPerCountry_ShouldReturnListOfCompanyDTO() {
        when(companyRepository.findAllByCountry("USA")).thenReturn(Collections.singletonList(company));
        when(companyMapper.mapToDTO(anyList())).thenReturn(Collections.singletonList(companyDTO));

        List<CompanyDTO> result = companyService.findAllCompaniesPerCountry("USA");

        assertEquals(1, result.size());
        assertEquals(companyDTO, result.get(0));
        verify(companyRepository, times(1)).findAllByCountry("USA");
        verify(companyMapper, times(1)).mapToDTO(anyList());
    }

    @Test
    @DisplayName("Find Company By Iata Code Test")
    void findCompanyByIata_ShouldReturnCompanyDTO_WhenCompanyExists() {
        when(companyRepository.findCompanyByIataCode("AC")).thenReturn(company);
        when(companyMapper.mapToDTO(company)).thenReturn(companyDTO);

        CompanyDTO result = companyService.findCompanyByIata("AC");

        assertEquals(companyDTO, result);
        verify(companyRepository, times(1)).findCompanyByIataCode("AC");
        verify(companyMapper, times(1)).mapToDTO(company);
    }

    @Test
    @DisplayName("Find Company by Iata throws CompanyNotFoundException Test")
    void findCompanyByIata_ShouldThrowCompanyNotFoundException_WhenCompanyDoesNotExist() {
        when(companyRepository.findCompanyByIataCode("AC")).thenReturn(null);

        assertThrows(CompanyNotFoundException.class, () -> companyService.findCompanyByIata("AC"));
        verify(companyRepository, times(1)).findCompanyByIataCode("AC");
        verify(companyMapper, never()).mapToDTO(any(Company.class));
    }

    @Test
    @DisplayName("Find Company By Name Test")
    void findCompanyByName_ShouldReturnCompanyDTO_WhenCompanyExists() {
        when(companyRepository.findCompanyByName("Airline Co.")).thenReturn(company);
        when(companyMapper.mapToDTO(company)).thenReturn(companyDTO);

        CompanyDTO result = companyService.findCompanyByName("Airline Co.");

        assertEquals(companyDTO, result);
        verify(companyRepository, times(1)).findCompanyByName("Airline Co.");
        verify(companyMapper, times(1)).mapToDTO(company);
    }

    @Test
    @DisplayName("Find Company by Name throws CompanyNotFoundException Test")
    void findCompanyByName_ShouldThrowCompanyNotFoundException_WhenCompanyDoesNotExist() {
        when(companyRepository.findCompanyByName("Airline Co.")).thenReturn(null);

        assertThrows(CompanyNotFoundException.class, () -> companyService.findCompanyByName("Airline Co."));
        verify(companyRepository, times(1)).findCompanyByName("Airline Co.");
        verify(companyMapper, never()).mapToDTO(any(Company.class));
    }

    @Test
    @DisplayName("Find Company by Id Test")
    void findCompanyById_ShouldReturnCompanyDTO_WhenCompanyExists() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyMapper.mapToDTO(company)).thenReturn(companyDTO);

        CompanyDTO result = companyService.findCompanyById(1L);

        assertEquals(companyDTO, result);
        verify(companyRepository, times(1)).findById(1L);
        verify(companyMapper, times(1)).mapToDTO(company);
    }

    @Test
    @DisplayName("Find Company by ID throws CompanyNotFoundException Test")
    void findCompanyById_ShouldThrowCompanyNotFoundException_WhenCompanyDoesNotExist() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> companyService.findCompanyById(1L));
        verify(companyRepository, times(1)).findById(1L);
        verify(companyMapper, never()).mapToDTO(any(Company.class));
    }


}
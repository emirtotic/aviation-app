package com.company.mapper;

import com.company.dto.CompanyDTO;
import com.company.entity.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyMapperTest {

    private CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);

    private Company company;
    private CompanyDTO companyDTO;

    @BeforeEach
    void setUp() {
        company = Company.builder()
                .id(1L)
                .name("Airline Company")
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
                .name("Airline Company")
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
    @DisplayName("Map CompanyDTO to Company")
    void mapToEntity_ShouldMapCompanyDTOToCompanyEntity() {
        Company result = companyMapper.mapToEntity(companyDTO);

        assertEquals(companyDTO.getId(), result.getId());
        assertEquals(companyDTO.getName(), result.getName());
        assertEquals(companyDTO.getYearFounded(), result.getYearFounded());
        assertEquals(companyDTO.getCountry(), result.getCountry());
        assertEquals(companyDTO.getIataCode(), result.getIataCode());
        assertEquals(companyDTO.getIcaoCode(), result.getIcaoCode());
        assertEquals(companyDTO.getFleetSize(), result.getFleetSize());
        assertEquals(companyDTO.getHeadquarters(), result.getHeadquarters());
        assertEquals(companyDTO.getDestinations(), result.getDestinations());
        assertEquals(companyDTO.getNumberOfEmployees(), result.getNumberOfEmployees());
        assertEquals(companyDTO.getWebsite(), result.getWebsite());
        assertEquals(companyDTO.getContactInfo(), result.getContactInfo());
    }

    @Test
    @DisplayName("Map List<CompanyDTO> to List<Company>")
    void mapToEntity_ShouldMapListOfCompanyDTOToCompanyEntities() {
        List<CompanyDTO> companyDTOList = List.of(companyDTO);
        List<Company> result = companyMapper.mapToEntity(companyDTOList);

        assertEquals(1, result.size());
        Company mappedCompany = result.get(0);

        assertEquals(companyDTO.getId(), mappedCompany.getId());
        assertEquals(companyDTO.getName(), mappedCompany.getName());
        assertEquals(companyDTO.getYearFounded(), mappedCompany.getYearFounded());
        assertEquals(companyDTO.getCountry(), mappedCompany.getCountry());
        assertEquals(companyDTO.getIataCode(), mappedCompany.getIataCode());
        assertEquals(companyDTO.getIcaoCode(), mappedCompany.getIcaoCode());
        assertEquals(companyDTO.getFleetSize(), mappedCompany.getFleetSize());
        assertEquals(companyDTO.getHeadquarters(), mappedCompany.getHeadquarters());
        assertEquals(companyDTO.getDestinations(), mappedCompany.getDestinations());
        assertEquals(companyDTO.getNumberOfEmployees(), mappedCompany.getNumberOfEmployees());
        assertEquals(companyDTO.getWebsite(), mappedCompany.getWebsite());
        assertEquals(companyDTO.getContactInfo(), mappedCompany.getContactInfo());
    }

    @Test
    @DisplayName("Map Company to CompanyDTO")
    void mapToDTO_ShouldMapCompanyEntityToCompanyDTO() {
        CompanyDTO result = companyMapper.mapToDTO(company);

        assertEquals(company.getId(), result.getId());
        assertEquals(company.getName(), result.getName());
        assertEquals(company.getYearFounded(), result.getYearFounded());
        assertEquals(company.getCountry(), result.getCountry());
        assertEquals(company.getIataCode(), result.getIataCode());
        assertEquals(company.getIcaoCode(), result.getIcaoCode());
        assertEquals(company.getFleetSize(), result.getFleetSize());
        assertEquals(company.getHeadquarters(), result.getHeadquarters());
        assertEquals(company.getDestinations(), result.getDestinations());
        assertEquals(company.getNumberOfEmployees(), result.getNumberOfEmployees());
        assertEquals(company.getWebsite(), result.getWebsite());
        assertEquals(company.getContactInfo(), result.getContactInfo());
    }

    @Test
    @DisplayName("Map List<Company> to List<CompanyDTO>")
    void mapToDTO_ShouldMapListOfCompanyEntitiesToCompanyDTOs() {
        List<Company> companyList = List.of(company);
        List<CompanyDTO> result = companyMapper.mapToDTO(companyList);

        assertEquals(1, result.size());
        CompanyDTO mappedDTO = result.get(0);

        assertEquals(company.getId(), mappedDTO.getId());
        assertEquals(company.getName(), mappedDTO.getName());
        assertEquals(company.getYearFounded(), mappedDTO.getYearFounded());
        assertEquals(company.getCountry(), mappedDTO.getCountry());
        assertEquals(company.getIataCode(), mappedDTO.getIataCode());
        assertEquals(company.getIcaoCode(), mappedDTO.getIcaoCode());
        assertEquals(company.getFleetSize(), mappedDTO.getFleetSize());
        assertEquals(company.getHeadquarters(), mappedDTO.getHeadquarters());
        assertEquals(company.getDestinations(), mappedDTO.getDestinations());
        assertEquals(company.getNumberOfEmployees(), mappedDTO.getNumberOfEmployees());
        assertEquals(company.getWebsite(), mappedDTO.getWebsite());
        assertEquals(company.getContactInfo(), mappedDTO.getContactInfo());
    }
}

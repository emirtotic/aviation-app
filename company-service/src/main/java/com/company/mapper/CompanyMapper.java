package com.company.mapper;

import com.company.dto.CompanyDTO;
import com.company.entity.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company mapToEntity(CompanyDTO companyDTO);

    List<Company> mapToEntity(List<CompanyDTO> companyDTOS);

    CompanyDTO mapToDTO(Company company);

    List<CompanyDTO> mapToDTO(List<Company> companies);

}

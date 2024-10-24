package com.company.controller;

import com.company.dto.CompanyDTO;
import com.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/all")
    public ResponseEntity<List<CompanyDTO>> findAllCompanies() {
        List<CompanyDTO> companies = companyService.findAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping("/all/company/country")
    public ResponseEntity<List<CompanyDTO>> findAllCompaniesPerCountry(@RequestParam String country) {
        List<CompanyDTO> companies = companyService.findAllCompaniesPerCountry(country);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping("/all/company/iata")
    public ResponseEntity<CompanyDTO> findCompanyByIata(@RequestParam String iata) {
        CompanyDTO company = companyService.findCompanyByIata(iata);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping("/all/company/name")
    public ResponseEntity<CompanyDTO> findCompanyByName(@RequestParam String name) {
        CompanyDTO company = companyService.findCompanyByName(name);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

}

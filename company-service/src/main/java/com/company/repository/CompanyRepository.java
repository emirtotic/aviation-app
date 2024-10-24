package com.company.repository;

import com.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.country LIKE %:country%")
    List<Company> findAllByCountry(@Param("country") String country);

    Company findCompanyByIataCode(String iata);

    @Query("SELECT c FROM Company c WHERE c.name LIKE %:name%")
    Company findCompanyByName(@Param("name") String name);
}

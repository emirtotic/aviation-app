package com.airport.service.impl;

import com.airport.entity.Country;
import com.airport.repository.CountryRepository;
import com.airport.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<String> findAllCountries() {
        return countryRepository.findAll().stream()
                .map(country -> new String(country.getName())).toList();
    }

    @Override
    public List<String> saveCountries(List<String> countryList) {
        countryList.forEach(c -> countryRepository.save(Country.builder().name(c).build()));
        return countryList;
    }
}

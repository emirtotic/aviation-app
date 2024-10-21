package com.airport.service;

import java.util.List;

public interface CountryService {

    List<String> findAllCountries();
    List<String> saveCountries(List<String> countryList);

}

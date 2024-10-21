package com.airport.controller;

import com.airport.service.CountryService;
import com.airport.service.impl.CountryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    private final CountryServiceImpl countryServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<String> refreshCountriesList(@RequestBody List<String> list) {
        List<String> response = countryService.saveCountries(list);
        return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
    }

}

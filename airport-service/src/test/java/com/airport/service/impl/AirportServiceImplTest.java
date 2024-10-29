package com.airport.service.impl;

import com.airport.dto.AirportDTO;
import com.airport.dto.AirportRestResponse;
import com.airport.entity.Airport;
import com.airport.exception.AirportNotFoundException;
import com.airport.mapper.AirportMapper;
import com.airport.repository.AirportRepository;
import com.airport.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AirportServiceImplTest {

    private AirportRepository airportRepository;

    private RestTemplate restTemplate;

    private AirportMapper airportMapper;

    private CountryService countryService;

    private AirportServiceImpl airportService;

    private Airport airport;
    private AirportDTO airportDTO;
    private AirportRestResponse airportRestResponse;

    @BeforeEach
    void setUp() {

        airportRepository = mock(AirportRepository.class);
        restTemplate = mock(RestTemplate.class);
        airportMapper = mock(AirportMapper.class);
        countryService = mock(CountryService.class);
        airportService = new AirportServiceImpl(airportRepository, restTemplate, airportMapper, countryService);

        airport = Airport.builder()
                .id("1")
                .name("John F Kennedy International Airport")
                .iata("JFK")
                .latitude(40.6413)
                .longitude(-73.7781)
                .country("USA")
                .build();

        airportDTO = AirportDTO.builder()
                .name("John F Kennedy International Airport")
                .iata("JFK")
                .latitude(40.6413)
                .longitude(-73.7781)
                .country("USA")
                .build();

        airportRestResponse = AirportRestResponse.builder()
                .name("John F Kennedy International Airport")
                .iata("JFK")
                .latitude(40.6413)
                .longitude(-73.7781)
                .country("USA")
                .build();
    }

    @Test
    @DisplayName("Find All Airports Test")
    void findAllAirports_ShouldReturnListOfAirportDTO() {
        when(airportRepository.findAll()).thenReturn(Collections.singletonList(airport));
        when(airportMapper.mapToDTO(anyList())).thenReturn(Collections.singletonList(airportDTO));

        List<AirportDTO> result = airportService.findAllAirports();

        assertEquals(1, result.size());
        assertEquals(airportDTO, result.get(0));
        verify(airportRepository, times(1)).findAll();
        verify(airportMapper, times(1)).mapToDTO(anyList());
    }

    @Test
    @DisplayName("Calculate Distance between Airports in KM Test")
    void calculateDistanceBetweenAirportsInKm_ShouldReturnDistance_WhenAirportsExist() {
        when(airportRepository.findByIata("JFK")).thenReturn(airport);
        Airport airport2 = Airport.builder()
                .id("2")
                .name("Los Angeles International Airport")
                .iata("LAX")
                .latitude(33.9416)
                .longitude(-118.4085)
                .country("USA")
                .build();
        when(airportRepository.findByIata("LAX")).thenReturn(airport2);

        BigDecimal result = airportService.calculateDistanceBetweenAirportsInKm("JFK", "LAX");

        assertNotNull(result);
        verify(airportRepository, times(2)).findByIata(anyString());
    }

    @Test
    @DisplayName("Calculate Distance between Airports in KM throws Exception Test")
    void calculateDistanceBetweenAirportsInKm_ShouldThrowException_WhenAirportNotFound() {
        when(airportRepository.findByIata("JFK")).thenReturn(null);

        assertThrows(AirportNotFoundException.class, () -> airportService.calculateDistanceBetweenAirportsInKm("JFK", "LAX"));
        verify(airportRepository, times(1)).findByIata("JFK");
    }

    @Test
    @DisplayName("Find Airport by IATA Code Test")
    void findAirportByIata_ShouldReturnAirportDTO_WhenAirportExists() {
        when(airportRepository.findByIata("JFK")).thenReturn(airport);
        when(airportMapper.mapToDTO(airport)).thenReturn(airportDTO);

        AirportDTO result = airportService.findAirportByIata("JFK");

        assertEquals(airportDTO, result);
        verify(airportRepository, times(1)).findByIata("JFK");
        verify(airportMapper, times(1)).mapToDTO(airport);
    }

    @Test
    @DisplayName("Find Airport by IATA Code throws Exception Test")
    void findAirportByIata_ShouldThrowException_WhenAirportNotFound() {
        when(airportRepository.findByIata("JFK")).thenReturn(null);

        assertThrows(AirportNotFoundException.class, () -> airportService.findAirportByIata("JFK"));
        verify(airportRepository, times(1)).findByIata("JFK");
    }

    @Test
    @DisplayName("Calculate Flight Duration In Minutes Test")
    void calculateFlightDuration_ShouldReturnDuration_WhenAirportsExist() {
        when(airportRepository.findByIata("JFK")).thenReturn(airport);
        Airport airport2 = Airport.builder()
                .id("2")
                .name("Los Angeles International Airport")
                .iata("LAX")
                .latitude(33.9416)
                .longitude(-118.4085)
                .country("USA")
                .build();
        when(airportRepository.findByIata("LAX")).thenReturn(airport2);

        BigDecimal result = airportService.calculateFlightDuration("JFK", "LAX");

        assertNotNull(result);
        verify(airportRepository, times(4)).findByIata(anyString());
    }

    @Test
    @DisplayName("Retrieve All Airports from Third Party Test")
    void fetchAllAirports_ShouldFetchAndSaveAirports() throws JsonProcessingException {
        when(countryService.findAllCountries()).thenReturn(List.of("USA"));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{ \"data\": [{ \"name\": \"JFK\", \"iata\": \"JFK\" }] }", HttpStatus.OK));
        when(airportMapper.mapRestResponseToEntity(any())).thenReturn(airport);

        List<AirportDTO> result = airportService.fetchAllAirports();

        assertNotNull(result);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class));
        verify(airportRepository, times(1)).save(any(Airport.class));
    }
}

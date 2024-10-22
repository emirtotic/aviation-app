package com.airport.service;

import com.airport.dto.AirportDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AirportService {

    List<AirportDTO> fetchAllAirports();
    List<AirportDTO> findAllAirports();
    BigDecimal calculateDistanceBetweenAirportsInKm(String iata1, String iata2);
    BigDecimal calculateFlightDuration(String iata1, String iata2);
    AirportDTO findAirportByIata(String iata);
}

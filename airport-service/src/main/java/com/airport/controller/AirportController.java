package com.airport.controller;

import com.airport.dto.AirportDTO;
import com.airport.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/airport")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/fetch")
    public ResponseEntity<List<AirportDTO>> fetchAllAirportsFromApi() {
        List<AirportDTO> airports = airportService.fetchAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirportDTO>> findAllAirports() {
        List<AirportDTO> airportDTOS = airportService.findAllAirports();
        return new ResponseEntity<>(airportDTOS, HttpStatus.OK);
    }

    // TODO: Move to Flight Service
    @PostMapping("/calculate-distance")
    public ResponseEntity<String> calculateDistanceInKm(@RequestParam String iata1, @RequestParam String iata2) {
        BigDecimal distance = airportService.calculateDistanceBetweenAirportsInKm(iata1, iata2);

        StringBuilder sb = new StringBuilder();
        sb.append("Distance between ")
                .append(iata1.toUpperCase())
                .append(" and ")
                .append(iata2.toUpperCase())
                .append(" is ")
                .append(distance)
                .append(" km.")
                .toString();

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    // TODO: Move to Flight Service
    @PostMapping("/calculate-flight-duration")
    public ResponseEntity<String> calculateFlightDuration(@RequestParam String iata1, @RequestParam String iata2) {
        BigDecimal distance = airportService.calculateFlightDuration(iata1, iata2);

        StringBuilder sb = new StringBuilder();
        sb.append("Duration between ")
                .append(iata1.toUpperCase())
                .append(" and ")
                .append(iata2.toUpperCase())
                .append(" is ")
                .append(distance)
                .append(" h.")
                .toString();

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

}

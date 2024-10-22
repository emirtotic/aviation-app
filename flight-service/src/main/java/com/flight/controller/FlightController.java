package com.flight.controller;

import com.flight.dto.FlightDto;
import com.flight.dto.FlightRequest;
import com.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/create")
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightRequest flightRequest) {

        FlightDto response = flightService.createFlight(flightRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

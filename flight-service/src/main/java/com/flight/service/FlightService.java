package com.flight.service;

import com.flight.dto.FlightDto;
import com.flight.dto.FlightRequest;

public interface FlightService {

    FlightDto createFlight(FlightRequest flightRequest);


}

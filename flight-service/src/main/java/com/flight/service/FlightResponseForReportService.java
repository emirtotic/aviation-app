package com.flight.service;

import com.flight.entity.FlightResponseForReport;

public interface FlightResponseForReportService {

    FlightResponseForReport findByFlightCode(String flightCode);
    FlightResponseForReport findById(Long id);
    void saveFlightResponseForReport(FlightResponseForReport flightResponseForReport);

}

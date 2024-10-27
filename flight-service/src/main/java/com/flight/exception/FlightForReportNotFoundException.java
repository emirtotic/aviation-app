package com.flight.exception;

public class FlightForReportNotFoundException extends RuntimeException {

    public FlightForReportNotFoundException(String parameter) {
        super("Flight for report not found: [" + parameter + "]");
    }

    public FlightForReportNotFoundException(Long id) {
        super("Flight for report not found: [" + id + "]");
    }

}


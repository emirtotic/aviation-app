package com.airport.exception;

public class AirportNotFoundException extends RuntimeException {

    public AirportNotFoundException(String parameter) {
        super("Airport not found: [" + parameter + "]");
    }

}


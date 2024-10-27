package com.report.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FlightReport {

    private Passenger passenger;
    private String planeModel;
    private int averagePlaneSpeed;
    private String departureAirport;
    private String arrivalAirport;
    private String createdAt;
    private String departureTime;
    private String arrivalTime;
    private Company company;
    private double flightDistanceKm;
    private double flightDurationInMinutes;
}


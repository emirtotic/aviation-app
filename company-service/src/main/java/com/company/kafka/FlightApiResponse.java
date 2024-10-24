package com.company.kafka;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightApiResponse {

    private String name;
    private String flightCode;
    private int yearFounded;
    private String country;
    private String iataCode;
    private String icaoCode;
    private int fleetSize;
    private String headquarters;
    private int destinations;
    private int numberOfEmployees;
    private String website;
    private String contactInfo;

}

package com.airport.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AirportRestResponse {

    private String name;
    private String iata;
    private String icao;
    private double latitude;
    private double longitude;
    private String country;
    private String region;
    private int alt;
    private int elevation;
    private String timezone;
    private String website;
    private String wikipedia;
}

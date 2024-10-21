package com.airport.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AirportDTO {

    private String id;
    private String name;
    private String iata;
    private String icao;
    private double latitude;
    private double longitude;
    private String country;
    private String region;
    private String timezone;
    private String website;
    private String wikipedia;

}
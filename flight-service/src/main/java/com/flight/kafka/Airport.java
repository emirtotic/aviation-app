package com.flight.kafka;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Airport {

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

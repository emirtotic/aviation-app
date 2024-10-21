package com.airport.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "airport")
public class Airport {

    @Id
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

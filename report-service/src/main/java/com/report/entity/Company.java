package com.report.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Company {

    private String name;
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

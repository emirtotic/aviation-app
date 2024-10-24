package com.company.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "companies", name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int yearFounded;
    private String country;
    private String iataCode; // IATA code of the company
    private String icaoCode; // ICAO code of the company
    private int fleetSize; // Number of aircraft in the fleet
    private String headquarters; // Headquarters location (city)
    private int destinations; // Number of destinations covered by the company
    private int numberOfEmployees; // Number of employees
    private String website; // Company's official website
    private String contactInfo;
}

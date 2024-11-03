package com.flight.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "flights", name = "flight_response_for_report")
public class FlightResponseForReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightCode;
    @Lob
    private String response;

    @Override
    public String toString() {
        return "FlightResponseForReport{" +
                "response='" + response + '\'' +
                '}';
    }
}

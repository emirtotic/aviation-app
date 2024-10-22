package com.airport.kafka;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightApiResponse {

    private String flightCode;
    private String departureAirport;
    private String arrivalAirport;

}

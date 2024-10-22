package com.flight.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AirportResponse {

    @JsonProperty("flightCode")
    private String flightCode;
    @JsonProperty("departureAirport")
    private String departureAirport;
    @JsonProperty("arrivalAirport")
    private String arrivalAirport;

}

package com.flight.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AirportApiResponse {

    @JsonProperty("flightCode")
    private String flightCode;
    @JsonProperty("departureAirport")
    @JsonDeserialize(using = AirportDeserializer.class)
    private Airport departureAirport;
    @JsonProperty("arrivalAirport")
    @JsonDeserialize(using = AirportDeserializer.class)
    private Airport arrivalAirport;

}

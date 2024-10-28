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
    @JsonProperty("departureAirportIata")
    private String departureAirportIata;
    @JsonProperty("departureAirportCountry")
    private String departureAirportCountrya;
    @JsonProperty("arrivalAirport")
    private String arrivalAirport;
    @JsonProperty("arrivalAirportIata")
    private String arrivalAirportIata;
    @JsonProperty("arrivalAirportCountry")
    private String arrivalAirportCountry;

}

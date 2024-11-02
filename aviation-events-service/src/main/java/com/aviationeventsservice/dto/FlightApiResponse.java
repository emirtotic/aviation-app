package com.aviationeventsservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightApiResponse {

    private String flightCode;
    private String events;
}

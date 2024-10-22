package com.plane.kafka;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightApiResponse {

    private String flightCode;
    private String model;
    private int capacity;
    private String type;
    private String description;
    private int averageSpeed;

}

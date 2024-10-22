package com.flight.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlaneResponse {

    private String flightCode;
    @JsonProperty("model")
    private String planeModel;

}

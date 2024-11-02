package com.flight.kafka;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AviationEventResponse {

    private String flightCode;
    private String events;

}

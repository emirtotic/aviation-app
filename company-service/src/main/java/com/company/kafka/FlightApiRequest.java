package com.company.kafka;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightApiRequest {

    private String flightCode;
    private long companyId;
}

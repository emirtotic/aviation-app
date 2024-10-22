package com.flight.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightRequest {

    private String flightCode;
    private long planeId;
    private String departureAirport;
    private String arrivalAirport;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departureTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrivalTime;

    /*
    U response kreiraj Plane response sa informacijama o avionu
    U response kreiraj Dva polja tipa Airport response sa informacijama o tome
     */

}

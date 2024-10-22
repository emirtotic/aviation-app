package com.flight.dto;

import com.flight.entity.Passenger;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightDto {

    private Long id;
    private List<Passenger> passengers;
    private long planeId;
    private String planeModel;
    private int averagePlaneSpeed;
    private String departureAirport;
    private String arrivalAirport;
    private Date createdAt;
    private Date departureTime;
    private Date arrivalTime;
    private String company;
    private BigDecimal flightDistance;
    private BigDecimal flightDuration;

}

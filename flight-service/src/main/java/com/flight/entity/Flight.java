package com.flight.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "flights", name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers;
    private String planeModel;
    private String departureAirport;
    private String arrivalAirport;
    private Date createdAt;
    private Date departureTime;
    private Date arrivalTime;
    private String company;

}

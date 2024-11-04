package com.flight.entity;

import com.flight.enums.Gender;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "flights", name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private Gender gender;
    private int age;
    private String email;
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
}

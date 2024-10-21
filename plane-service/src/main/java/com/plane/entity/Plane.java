package com.plane.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "plane", name = "plane")
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private PlaneType type;
    private String description;
    private int averageSpeed;
}




package com.report.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Passenger {

    private String firstName;
    private String lastName;
    private String title;
    private String gender;
    private String email;
    private int age;
}

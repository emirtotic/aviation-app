package com.flight.dto;

import com.flight.enums.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PassengerDTO {

    private String firstName;
    private String lastName;
    private String title;
    private Gender gender;
    private int age;
}

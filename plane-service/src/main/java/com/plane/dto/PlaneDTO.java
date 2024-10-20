package com.plane.dto;

import com.plane.entity.PlaneType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlaneDTO {

    @NotBlank(message = "Model is required.")
    @Size(min = 3, message = "Model must be at least 3 characters long.")
    private String model;

    @Min(value = 7, message = "Capacity must be at least 7.")
    private int capacity;

    @NotNull(message = "Type is required.")
    private PlaneType type;

    @NotBlank(message = "Description cannot be empty.")
    private String description;
}

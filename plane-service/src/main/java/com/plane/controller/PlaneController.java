package com.plane.controller;

import com.plane.dto.PlaneDTO;
import com.plane.service.PlaneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
@Tag(name = "Plane Service", description = "Operations related to Plane Service")
@RequiredArgsConstructor
public class PlaneController {

    private final PlaneService planeService;

    @Operation(summary = "Find All Planes", description = "Retrieve all planes from DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All planes retrieved")
    })
    @GetMapping("/all")
    public ResponseEntity<List<PlaneDTO>> findAllPlanes() {

        List<PlaneDTO> planeDTOList = planeService.findAllPlanes();

        return new ResponseEntity<>(planeDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Find Planes By Type", description = "Retrieve all planes of a specific type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planes of specified type retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No planes found for the specified type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/all/type/{type}")
    public ResponseEntity<List<PlaneDTO>> findAllByType(@PathVariable String type) {

        List<PlaneDTO> planeDTOList = planeService.findAllPlanesByType(type);

        return new ResponseEntity<>(planeDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Find Planes By Capacity", description = "Retrieve all planes with capacity greater than the specified value")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planes with specified capacity retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No planes found with the specified capacity"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/all/capacity/{capacity}")
    public ResponseEntity<List<PlaneDTO>> findAllByCapacity(@PathVariable int capacity) {

        List<PlaneDTO> planeDTOList = planeService.findAllPlanesByCapacity(capacity);

        return new ResponseEntity<>(planeDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Find Planes By Manufacturer", description = "Retrieve all planes manufactured by a specific manufacturer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planes from specified manufacturer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No planes found from the specified manufacturer"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/all/manufacturer/{manufacturer}")
    public ResponseEntity<List<PlaneDTO>> findAllByManufacturer(@PathVariable String manufacturer) {

        List<PlaneDTO> planeDTOList = planeService.findAllPlanesByManufacturer(manufacturer);

        return new ResponseEntity<>(planeDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Create New Plane", description = "Create a new plane in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plane created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<PlaneDTO> createNewPlane(@Valid @RequestBody PlaneDTO planeDTO) {

        PlaneDTO newPlane = planeService.createNewPlane(planeDTO);

        return new ResponseEntity<>(newPlane, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Plane", description = "Update an existing plane in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plane updated successfully"),
            @ApiResponse(responseCode = "404", description = "Plane not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update")
    public ResponseEntity<PlaneDTO> updatePlane(@RequestParam Long id, @Valid @RequestBody PlaneDTO planeDTO) {

        PlaneDTO newPlane = planeService.updatePlane(id, planeDTO);

        return new ResponseEntity<>(newPlane, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Plane", description = "Delete a plane from the database by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plane deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Plane not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlane(@RequestParam Long id) {

        String result = planeService.deletePlane(id);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}

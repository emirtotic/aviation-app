package com.plane.controller;

import com.plane.dto.PlaneDTO;
import com.plane.service.PlaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlaneController {

    private final PlaneService planeService;

    @GetMapping("/all")
    public ResponseEntity<List<PlaneDTO>> findAllPlanes() {

        List<PlaneDTO> planeDTOList = planeService.findAllPlanes();

        return new ResponseEntity<>(planeDTOList, HttpStatus.OK);
    }

    @PostMapping("/all/type/{type}")
    public ResponseEntity<List<PlaneDTO>> findAllByType(@PathVariable String type) {

        List<PlaneDTO> planeDTOList = planeService.findAllPlanesByType(type);

        return new ResponseEntity<>(planeDTOList, HttpStatus.OK);
    }

    @PostMapping("/all/capacity/{capacity}")
    public ResponseEntity<List<PlaneDTO>> findAllByCapacity(@PathVariable int capacity) {

        List<PlaneDTO> planeDTOList = planeService.findAllPlanesByCapacity(capacity);

        return new ResponseEntity<>(planeDTOList, HttpStatus.OK);
    }

    @PostMapping("/all/manufacturer/{manufacturer}")
    public ResponseEntity<List<PlaneDTO>> findAllByManufacturer(@PathVariable String manufacturer) {

        List<PlaneDTO> planeDTOList = planeService.findAllPlanesByManufacturer(manufacturer);

        return new ResponseEntity<>(planeDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PlaneDTO> createNewPlane(@Valid @RequestBody PlaneDTO planeDTO) {

        PlaneDTO newPlane = planeService.createNewPlane(planeDTO);

        return new ResponseEntity<>(newPlane, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<PlaneDTO> updatePlane(@RequestParam Long id, @Valid @RequestBody PlaneDTO planeDTO) {

        PlaneDTO newPlane = planeService.updatePlane(id, planeDTO);

        return new ResponseEntity<>(newPlane, HttpStatus.CREATED);
    }


}

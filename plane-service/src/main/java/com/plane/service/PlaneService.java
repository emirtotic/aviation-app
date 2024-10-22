package com.plane.service;

import com.plane.dto.PlaneDTO;

import java.util.List;

public interface PlaneService {

    List<PlaneDTO> findAllPlanes();

    List<PlaneDTO> findAllPlanesByType(String type);

    List<PlaneDTO> findAllPlanesByCapacity(int capacity);

    List<PlaneDTO> findAllPlanesByManufacturer(String manufacturer);

    PlaneDTO createNewPlane(PlaneDTO planeDTO);

    PlaneDTO updatePlane(Long id, PlaneDTO updatedPlane);

    PlaneDTO findPlaneById(Long id);

    String deletePlane(Long id);

}

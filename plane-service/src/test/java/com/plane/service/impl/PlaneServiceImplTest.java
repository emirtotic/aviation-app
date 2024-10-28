package com.plane.service.impl;

import com.plane.dto.PlaneDTO;
import com.plane.entity.Plane;
import com.plane.enums.PlaneType;
import com.plane.exception.PlaneNotFoundException;
import com.plane.mapper.PlaneMapper;
import com.plane.repository.PlaneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaneServiceImplTest {

    private PlaneRepository planeRepository;

    private PlaneMapper planeMapper;

    private PlaneServiceImpl planeService;

    private Plane plane;
    private PlaneDTO planeDTO;

    @BeforeEach
    void setUp() {

        planeRepository = mock(PlaneRepository.class);
        planeMapper = mock(PlaneMapper.class);
        planeService = new PlaneServiceImpl(planeRepository, planeMapper);

        plane = Plane.builder()
                .id(1L)
                .model("Boeing 747")
                .capacity(400)
                .type(PlaneType.PASSENGER)
                .description("A large commercial jet")
                .build();

        planeDTO = PlaneDTO.builder()
                .model("Boeing 747")
                .capacity(400)
                .type(PlaneType.PASSENGER)
                .description("A large commercial jet")
                .build();
    }

    @Test
    @DisplayName("Find All Planes")
    void findAllPlanes_ShouldReturnPlaneDTOList() {
        when(planeRepository.findAll()).thenReturn(Collections.singletonList(plane));
        when(planeMapper.mapToDTO(plane)).thenReturn(planeDTO);

        List<PlaneDTO> result = planeService.findAllPlanes();

        assertEquals(1, result.size());
        assertEquals(planeDTO, result.get(0));
        verify(planeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Find All Planes By Type")
    void findAllPlanesByType_ShouldReturnPlaneDTOList() {
        when(planeRepository.findAllByType(PlaneType.PASSENGER)).thenReturn(Collections.singletonList(plane));
        when(planeMapper.mapToDTO(plane)).thenReturn(planeDTO);

        List<PlaneDTO> result = planeService.findAllPlanesByType("PASSENGER");

        assertEquals(1, result.size());
        assertEquals(planeDTO, result.get(0));
        verify(planeRepository, times(1)).findAllByType(PlaneType.PASSENGER);
    }

    @Test
    @DisplayName("Find All Planes By Capacity")
    void findAllPlanesByCapacity_ShouldReturnPlaneDTOList() {
        when(planeRepository.findAllByCapacityGreaterThan(300)).thenReturn(Collections.singletonList(plane));
        when(planeMapper.mapToDTO(plane)).thenReturn(planeDTO);

        List<PlaneDTO> result = planeService.findAllPlanesByCapacity(300);

        assertEquals(1, result.size());
        assertEquals(planeDTO, result.get(0));
        verify(planeRepository, times(1)).findAllByCapacityGreaterThan(300);
    }

    @Test
    @DisplayName("Find All Planes By Manufacturer")
    void findAllPlanesByManufacturer_ShouldReturnPlaneDTOList() {
        when(planeRepository.findAllByModelLikeIgnoreCase("Boeing%")).thenReturn(Collections.singletonList(plane));
        when(planeMapper.mapToDTO(plane)).thenReturn(planeDTO);

        List<PlaneDTO> result = planeService.findAllPlanesByManufacturer("Boeing");

        assertEquals(1, result.size());
        assertEquals(planeDTO, result.get(0));
        verify(planeRepository, times(1)).findAllByModelLikeIgnoreCase("Boeing%");
    }

    @Test
    @DisplayName("Create new Plane")
    void createNewPlane_ShouldReturnCreatedPlaneDTO() {

        when(planeRepository.save(any(Plane.class))).thenReturn(plane);
        when(planeMapper.mapToDTO(any(Plane.class))).thenReturn(planeDTO);
        PlaneDTO result = planeService.createNewPlane(planeDTO);

        assertEquals(planeDTO, result, "Expected created PlaneDTO to match");
        verify(planeRepository, times(1)).save(any(Plane.class));
        verify(planeMapper, times(1)).mapToDTO(any(Plane.class));
    }

    @Test
    @DisplayName("Update Plane")
    void updatePlane_ShouldReturnUpdatedPlaneDTO() {
        when(planeRepository.findById(1L)).thenReturn(Optional.of(plane));
        when(planeRepository.save(any(Plane.class))).thenReturn(plane);
        when(planeMapper.mapToDTO(any(Plane.class))).thenReturn(planeDTO);

        PlaneDTO updatedDTO = PlaneDTO.builder()
                .model("Boeing 747")
                .capacity(400)
                .type(PlaneType.PASSENGER)
                .description("Updated description")
                .build();

        PlaneDTO result = planeService.updatePlane(1L, updatedDTO);

        assertEquals(updatedDTO.getModel(), result.getModel());
        assertEquals(updatedDTO.getCapacity(), result.getCapacity());
        verify(planeRepository, times(1)).findById(1L);
        verify(planeRepository, times(1)).save(any(Plane.class));
    }

    @Test
    @DisplayName("Find Plane by ID")
    void findPlaneById_ShouldReturnPlaneDTO_WhenPlaneExists() {
        when(planeRepository.findById(1L)).thenReturn(Optional.of(plane));
        when(planeMapper.mapToDTO(plane)).thenReturn(planeDTO);

        PlaneDTO result = planeService.findPlaneById(1L);

        assertEquals(planeDTO, result);
        verify(planeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Find Plane by ID throws PlaneNotFoundException")
    void findPlaneById_ShouldThrowPlaneNotFoundException_WhenPlaneDoesNotExist() {
        when(planeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PlaneNotFoundException.class, () -> planeService.findPlaneById(1L));
        verify(planeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Delete Plane")
    void deletePlane_ShouldReturnSuccessMessage_WhenPlaneExists() {
        when(planeRepository.findById(1L)).thenReturn(Optional.of(plane));

        String result = planeService.deletePlane(1L);

        assertEquals("Plane successfully deleted with ID: 1", result);
        verify(planeRepository, times(1)).findById(1L);
        verify(planeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete Plane throws PlaneNotFoundException")
    void deletePlane_ShouldThrowPlaneNotFoundException_WhenPlaneDoesNotExist() {
        when(planeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PlaneNotFoundException.class, () -> planeService.deletePlane(1L));
        verify(planeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Convert String to Plane Type Enum")
    void convertStringToPlaneType_ShouldReturnCorrectPlaneType() {
        PlaneType result = planeService.convertStringToPlaneType("PASSENGER");
        assertEquals(PlaneType.PASSENGER, result);
    }

    @Test
    @DisplayName("Convert String to Plane Type Enum throws exception")
    void convertStringToPlaneType_ShouldThrowIllegalArgumentException_WhenInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> planeService.convertStringToPlaneType("INVALID_TYPE"));
    }
}
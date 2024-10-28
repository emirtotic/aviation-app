package com.plane.mapper;

import com.plane.dto.PlaneDTO;
import com.plane.entity.Plane;
import com.plane.enums.PlaneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneMapperTest {

    private final PlaneMapper planeMapper = Mappers.getMapper(PlaneMapper.class);

    @Test
    @DisplayName("Map FlightDTO to Flight")
    void mapToEntity_ShouldMapPlaneDTOToPlaneEntity() {

        PlaneDTO planeDTO = new PlaneDTO();
        planeDTO.setModel("Boeing 737");
        planeDTO.setCapacity(150);
        planeDTO.setType(PlaneType.PASSENGER);
        planeDTO.setDescription("A commercial aircraft");
        planeDTO.setAverageSpeed(500);

        Plane plane = planeMapper.mapToEntity(planeDTO);

        assertNull(plane.getId(), "ID should be null since it is ignored in mapping.");
        assertEquals(planeDTO.getModel(), plane.getModel());
        assertEquals(planeDTO.getCapacity(), plane.getCapacity());
        assertEquals(planeDTO.getType(), plane.getType());
        assertEquals(planeDTO.getDescription(), plane.getDescription());
        assertEquals(planeDTO.getAverageSpeed(), plane.getAverageSpeed());
    }

    @Test
    @DisplayName("Map List<FlightDTO> to List<Flight>")
    void mapToEntity_ShouldMapListOfPlaneDTOToPlaneEntities() {

        PlaneDTO planeDTO = new PlaneDTO();
        planeDTO.setModel("Boeing 737");
        planeDTO.setCapacity(150);
        planeDTO.setType(PlaneType.PASSENGER);
        planeDTO.setDescription("A commercial aircraft");
        planeDTO.setAverageSpeed(500);

        List<PlaneDTO> planeDTOList = List.of(planeDTO);

        List<Plane> planes = planeMapper.mapToEntity(planeDTOList);

        assertEquals(1, planes.size());
        Plane plane = planes.get(0);
        assertNull(plane.getId(), "ID should be null since it is ignored in mapping.");
        assertEquals(planeDTO.getModel(), plane.getModel());
        assertEquals(planeDTO.getCapacity(), plane.getCapacity());
        assertEquals(planeDTO.getType(), plane.getType());
        assertEquals(planeDTO.getDescription(), plane.getDescription());
        assertEquals(planeDTO.getAverageSpeed(), plane.getAverageSpeed());
    }

    @Test
    @DisplayName("Map Flight to FlightDTO")
    void mapToDTO_ShouldMapPlaneEntityToPlaneDTO() {

        Plane plane = new Plane();
        plane.setId(1L);
        plane.setModel("Airbus A320");
        plane.setCapacity(200);
        plane.setType(PlaneType.PASSENGER);
        plane.setDescription("A commercial jet");
        plane.setAverageSpeed(600);

        PlaneDTO planeDTO = planeMapper.mapToDTO(plane);

        assertEquals(plane.getModel(), planeDTO.getModel());
        assertEquals(plane.getCapacity(), planeDTO.getCapacity());
        assertEquals(plane.getType(), planeDTO.getType());
        assertEquals(plane.getDescription(), planeDTO.getDescription());
        assertEquals(plane.getAverageSpeed(), planeDTO.getAverageSpeed());
    }

    @Test
    @DisplayName("Map List<Flight> to List<FlightDTO>")
    void mapToDTO_ShouldMapListOfPlaneEntitiesToPlaneDTOs() {

        Plane plane = new Plane();
        plane.setId(1L);
        plane.setModel("Airbus A320");
        plane.setCapacity(200);
        plane.setType(PlaneType.PASSENGER);
        plane.setDescription("A commercial jet");
        plane.setAverageSpeed(600);

        List<Plane> planeList = List.of(plane);

        List<PlaneDTO> planeDTOs = planeMapper.mapToDTO(planeList);

        assertEquals(1, planeDTOs.size());
        PlaneDTO planeDTO = planeDTOs.get(0);
        assertEquals(plane.getModel(), planeDTO.getModel());
        assertEquals(plane.getCapacity(), planeDTO.getCapacity());
        assertEquals(plane.getType(), planeDTO.getType());
        assertEquals(plane.getDescription(), planeDTO.getDescription());
        assertEquals(plane.getAverageSpeed(), planeDTO.getAverageSpeed());
    }
}
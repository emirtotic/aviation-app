package com.plane.mapper;

import com.plane.dto.PlaneDTO;
import com.plane.entity.Plane;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaneMapper {

    @Mapping(target = "id", ignore = true)
    Plane mapToEntity(PlaneDTO planeDTO);

    List<Plane> mapToEntity(List<PlaneDTO> planeDTOS);

    PlaneDTO mapToDTO(Plane plane);

    List<PlaneDTO> mapToDTO(List<Plane> planes);

}

package com.flight.mapper;

import com.flight.dto.FlightDto;
import com.flight.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(target = "company", source = "flightDto.company.name")
    Flight mapToEntity(FlightDto flightDto);

    List<Flight> mapToEntity(List<FlightDto> flightDtos);

    @Mapping(target = "company", ignore = true)
    FlightDto mapToDTO(Flight flight);

    List<FlightDto> mapToDTO(List<Flight> flights);

}

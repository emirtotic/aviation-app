package com.flight.mapper;

import com.flight.dto.FlightDto;
import com.flight.entity.Flight;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    Flight mapToEntity(FlightDto flightDto);

    List<Flight> mapToEntity(List<FlightDto> flightDtos);

    FlightDto mapToDTO(Flight flight);

    List<FlightDto> mapToDTO(List<Flight> flights);

}

package com.airport.mapper;

import com.airport.dto.AirportDTO;
import com.airport.dto.AirportRestResponse;
import com.airport.entity.Airport;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirportMapper {

    Airport mapToEntity(AirportDTO airportDTO);

    List<Airport> mapToEntity(List<AirportDTO> airportDTOS);

    AirportDTO mapToDTO(Airport airport);

    List<AirportDTO> mapToDTO(List<Airport> airports);

    Airport mapRestResponseToEntity(AirportRestResponse airportRestResponse);
}

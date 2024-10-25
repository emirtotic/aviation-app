package com.flight.mapper;

import com.flight.dto.PassengerDTO;
import com.flight.entity.Passenger;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {


    Passenger mapToEntity(PassengerDTO passengerDTO);

    List<Passenger> mapToEntity(List<PassengerDTO> passengerDTOS);

    PassengerDTO mapToDTO(Passenger passenger);

    List<PassengerDTO> mapToDTO(List<Passenger> passengers);

}

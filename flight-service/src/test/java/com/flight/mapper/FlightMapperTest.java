package com.flight.mapper;

import com.flight.dto.CompanyDetails;
import com.flight.dto.FlightDto;
import com.flight.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FlightMapperTest {

    private FlightMapper flightMapper = Mappers.getMapper(FlightMapper.class);

    private FlightDto flightDto;
    private Flight flight;
    private Date currentDate;
    private CompanyDetails companyDetails;

    @BeforeEach
    void setUp() {
        currentDate = new Date();

        companyDetails = CompanyDetails.builder()
                .name("Airline Co.")
                .yearFounded(1995)
                .country("USA")
                .iataCode("AC")
                .icaoCode("ACO")
                .fleetSize(100)
                .headquarters("New York")
                .destinations(50)
                .numberOfEmployees(5000)
                .website("http://www.airlineco.com")
                .contactInfo("contact@airlineco.com")
                .build();

        flightDto = FlightDto.builder()
                .planeModel("Boeing 747")
                .departureAirport("JFK")
                .departureAirportCountry("USA")
                .departureTime(currentDate)
                .arrivalAirport("LAX")
                .arrivalAirportCountry("USA")
                .arrivalTime(currentDate)
                .flightDistanceKm(BigDecimal.valueOf(4500))
                .flightDurationInMinutes(BigDecimal.valueOf(300))
                .company(companyDetails)
                .build();

        flight = Flight.builder()
                .id(1L)
                .planeModel("Boeing 747")
                .departureAirport("JFK")
                .arrivalAirport("LAX")
                .createdAt(currentDate)
                .departureTime(currentDate)
                .arrivalTime(currentDate)
                .company("Airline Co.")
                .build();
    }

    @Test
    @DisplayName("Map FlightDTO to Flight Test")
    void mapToEntity_ShouldMapFlightDtoToFlightEntity() {
        Flight result = flightMapper.mapToEntity(flightDto);

        assertNull(result.getId());
        assertEquals(flightDto.getPlaneModel(), result.getPlaneModel());
        assertEquals(flightDto.getDepartureAirport(), result.getDepartureAirport());
        assertEquals(flightDto.getArrivalAirport(), result.getArrivalAirport());
        assertEquals(flightDto.getDepartureTime(), result.getDepartureTime());
        assertEquals(flightDto.getArrivalTime(), result.getArrivalTime());
        assertEquals(flightDto.getCompany().getName(), result.getCompany());
    }

    @Test
    @DisplayName("Map List<FlightDTO> to List<Flight> Test")
    void mapToEntity_ShouldMapListOfFlightDtoToListOfFlightEntities() {
        List<FlightDto> flightDtos = Collections.singletonList(flightDto);
        List<Flight> result = flightMapper.mapToEntity(flightDtos);

        assertEquals(1, result.size());
        Flight mappedFlight = result.get(0);

        assertNull(mappedFlight.getId());
        assertEquals(flightDto.getPlaneModel(), mappedFlight.getPlaneModel());
        assertEquals(flightDto.getDepartureAirport(), mappedFlight.getDepartureAirport());
        assertEquals(flightDto.getArrivalAirport(), mappedFlight.getArrivalAirport());
        assertEquals(flightDto.getCompany().getName(), mappedFlight.getCompany());
    }

    @Test
    @DisplayName("Map Flight to FlightDTO Test")
    void mapToDTO_ShouldMapFlightEntityToFlightDto() {
        FlightDto result = flightMapper.mapToDTO(flight);

        assertEquals(flight.getPlaneModel(), result.getPlaneModel());
        assertEquals(flight.getDepartureAirport(), result.getDepartureAirport());
        assertEquals(flight.getArrivalAirport(), result.getArrivalAirport());
        assertEquals(flight.getDepartureTime(), result.getDepartureTime());
        assertEquals(flight.getArrivalTime(), result.getArrivalTime());
        assertNull(result.getCompany()); // Company is ignored in mapping
    }

    @Test
    @DisplayName("Map List<Flight> to List<FlightDTO> Test")
    void mapToDTO_ShouldMapListOfFlightEntitiesToListOfFlightDtos() {
        List<Flight> flights = Collections.singletonList(flight);
        List<FlightDto> result = flightMapper.mapToDTO(flights);

        assertEquals(1, result.size());
        FlightDto mappedDto = result.get(0);

        assertEquals(flight.getPlaneModel(), mappedDto.getPlaneModel());
        assertEquals(flight.getDepartureAirport(), mappedDto.getDepartureAirport());
        assertEquals(flight.getArrivalAirport(), mappedDto.getArrivalAirport());
        assertNull(mappedDto.getCompany());
    }
}

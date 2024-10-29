package com.airport.mapper;

import com.airport.dto.AirportDTO;
import com.airport.dto.AirportRestResponse;
import com.airport.entity.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AirportMapperTest {

    private AirportMapper airportMapper = Mappers.getMapper(AirportMapper.class);

    private Airport airport;
    private AirportDTO airportDTO;
    private AirportRestResponse airportRestResponse;

    @BeforeEach
    void setUp() {
        airport = Airport.builder()
                .id("1")
                .name("John F Kennedy International Airport")
                .iata("JFK")
                .icao("KJFK")
                .latitude(40.6413)
                .longitude(-73.7781)
                .country("USA")
                .region("New York")
                .timezone("America/New_York")
                .website("https://www.jfkairport.com")
                .wikipedia("https://en.wikipedia.org/wiki/John_F._Kennedy_International_Airport")
                .build();

        airportDTO = AirportDTO.builder()
                .name("John F Kennedy International Airport")
                .iata("JFK")
                .icao("KJFK")
                .latitude(40.6413)
                .longitude(-73.7781)
                .country("USA")
                .region("New York")
                .timezone("America/New_York")
                .website("https://www.jfkairport.com")
                .wikipedia("https://en.wikipedia.org/wiki/John_F._Kennedy_International_Airport")
                .build();

        airportRestResponse = AirportRestResponse.builder()
                .name("John F Kennedy International Airport")
                .iata("JFK")
                .icao("KJFK")
                .latitude(40.6413)
                .longitude(-73.7781)
                .country("USA")
                .region("New York")
                .alt(13)
                .elevation(13)
                .timezone("America/New_York")
                .website("https://www.jfkairport.com")
                .wikipedia("https://en.wikipedia.org/wiki/John_F._Kennedy_International_Airport")
                .build();
    }

    @Test
    @DisplayName("Map AirportDTO to Airport")
    void mapToEntity_ShouldMapAirportDTOToAirportEntity() {
        Airport result = airportMapper.mapToEntity(airportDTO);

        assertNull(result.getId());
        assertEquals(airportDTO.getName(), result.getName());
        assertEquals(airportDTO.getIata(), result.getIata());
        assertEquals(airportDTO.getIcao(), result.getIcao());
        assertEquals(airportDTO.getLatitude(), result.getLatitude());
        assertEquals(airportDTO.getLongitude(), result.getLongitude());
        assertEquals(airportDTO.getCountry(), result.getCountry());
        assertEquals(airportDTO.getRegion(), result.getRegion());
        assertEquals(airportDTO.getTimezone(), result.getTimezone());
        assertEquals(airportDTO.getWebsite(), result.getWebsite());
        assertEquals(airportDTO.getWikipedia(), result.getWikipedia());
    }

    @Test
    @DisplayName("Map List<AirportDTO> to List<Airport>")
    void mapToEntity_ShouldMapListOfAirportDTOToAirportEntities() {
        List<AirportDTO> airportDTOList = List.of(airportDTO);
        List<Airport> result = airportMapper.mapToEntity(airportDTOList);

        assertEquals(1, result.size());
        Airport mappedAirport = result.get(0);

        assertNull(mappedAirport.getId());
        assertEquals(airportDTO.getName(), mappedAirport.getName());
        assertEquals(airportDTO.getIata(), mappedAirport.getIata());
        assertEquals(airportDTO.getIcao(), mappedAirport.getIcao());
        assertEquals(airportDTO.getLatitude(), mappedAirport.getLatitude());
        assertEquals(airportDTO.getLongitude(), mappedAirport.getLongitude());
        assertEquals(airportDTO.getCountry(), mappedAirport.getCountry());
        assertEquals(airportDTO.getRegion(), mappedAirport.getRegion());
        assertEquals(airportDTO.getTimezone(), mappedAirport.getTimezone());
        assertEquals(airportDTO.getWebsite(), mappedAirport.getWebsite());
        assertEquals(airportDTO.getWikipedia(), mappedAirport.getWikipedia());
    }

    @Test
    @DisplayName("Map Airport to AirportDTO")
    void mapToDTO_ShouldMapAirportEntityToAirportDTO() {
        AirportDTO result = airportMapper.mapToDTO(airport);

        assertEquals(airport.getName(), result.getName());
        assertEquals(airport.getIata(), result.getIata());
        assertEquals(airport.getIcao(), result.getIcao());
        assertEquals(airport.getLatitude(), result.getLatitude());
        assertEquals(airport.getLongitude(), result.getLongitude());
        assertEquals(airport.getCountry(), result.getCountry());
        assertEquals(airport.getRegion(), result.getRegion());
        assertEquals(airport.getTimezone(), result.getTimezone());
        assertEquals(airport.getWebsite(), result.getWebsite());
        assertEquals(airport.getWikipedia(), result.getWikipedia());
    }

    @Test
    @DisplayName("Map List<Airport> to List<AirportDTO>")
    void mapToDTO_ShouldMapListOfAirportEntitiesToAirportDTOs() {
        List<Airport> airportList = List.of(airport);
        List<AirportDTO> result = airportMapper.mapToDTO(airportList);

        assertEquals(1, result.size());
        AirportDTO mappedDTO = result.get(0);

        assertEquals(airport.getName(), mappedDTO.getName());
        assertEquals(airport.getIata(), mappedDTO.getIata());
        assertEquals(airport.getIcao(), mappedDTO.getIcao());
        assertEquals(airport.getLatitude(), mappedDTO.getLatitude());
        assertEquals(airport.getLongitude(), mappedDTO.getLongitude());
        assertEquals(airport.getCountry(), mappedDTO.getCountry());
        assertEquals(airport.getRegion(), mappedDTO.getRegion());
        assertEquals(airport.getTimezone(), mappedDTO.getTimezone());
        assertEquals(airport.getWebsite(), mappedDTO.getWebsite());
        assertEquals(airport.getWikipedia(), mappedDTO.getWikipedia());
    }

    @Test
    @DisplayName("Map AirportResponse to Airport")
    void mapRestResponseToEntity_ShouldMapAirportRestResponseToAirportEntity() {
        Airport result = airportMapper.mapRestResponseToEntity(airportRestResponse);

        assertNull(result.getId());
        assertEquals(airportRestResponse.getName(), result.getName());
        assertEquals(airportRestResponse.getIata(), result.getIata());
        assertEquals(airportRestResponse.getIcao(), result.getIcao());
        assertEquals(airportRestResponse.getLatitude(), result.getLatitude());
        assertEquals(airportRestResponse.getLongitude(), result.getLongitude());
        assertEquals(airportRestResponse.getCountry(), result.getCountry());
        assertEquals(airportRestResponse.getRegion(), result.getRegion());
        assertEquals(airportRestResponse.getTimezone(), result.getTimezone());
        assertEquals(airportRestResponse.getWebsite(), result.getWebsite());
        assertEquals(airportRestResponse.getWikipedia(), result.getWikipedia());
    }
}


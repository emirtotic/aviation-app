package com.airport.service.impl;

import com.airport.dto.AirportDTO;
import com.airport.dto.AirportRestResponse;
import com.airport.entity.Airport;
import com.airport.exception.AirportNotFoundException;
import com.airport.kafka.AirportKafkaConsumer;
import com.airport.mapper.AirportMapper;
import com.airport.repository.AirportRepository;
import com.airport.service.AirportService;
import com.airport.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportServiceImpl implements AirportService {

    @Value("${rapidAirportApiUrl}")
    private String airports;
    @Value("${x-rapidapi-key}")
    private String key;
    @Value("${x-airport-rapidapi-host}")
    private String host;

    private static final int EARTH_RADIUS = 6371;

    private final AirportRepository airportRepository;
    private final RestTemplate restTemplate;
    private final AirportMapper airportMapper;
    private final CountryService countryService;


    @Override
    public List<AirportDTO> findAllAirports() {
        return airportMapper.mapToDTO(airportRepository.findAll());
    }

    @Override
    public BigDecimal calculateDistanceBetweenAirportsInKm(String iata1, String iata2) {

        Airport airport1 = Optional.ofNullable(airportRepository.findByIata(iata1.toUpperCase()))
                .orElseThrow(() -> new AirportNotFoundException(iata1));

        Airport airport2 = Optional.of(airportRepository.findByIata(iata2.toUpperCase()))
                .orElseThrow(() -> new AirportNotFoundException(iata2));

        StringBuilder sb = new StringBuilder();
        sb.append("Calculating distance between ")
                .append(airport1.getName()).append(" [")
                .append(airport1.getCountry()).append("] ")
                .append("and ")
                .append(airport2.getName()).append(" [")
                .append(airport2.getCountry()).append("]");

        log.info(sb.toString());

        double deltaLat = Math.toRadians(airport2.getLatitude() - airport1.getLatitude());
        double deltaLon = Math.toRadians(airport2.getLongitude() - airport1.getLongitude());

        double a = haversineFormula(deltaLat, deltaLon, airport1, airport2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return BigDecimal.valueOf(EARTH_RADIUS * c)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public AirportDTO findAirportByIata(String iata) {
        return airportMapper.mapToDTO(airportRepository.findByIata(iata.toUpperCase()));
    }

    @Override
    public BigDecimal calculateFlightDuration(String iata1, String iata2) {

        Airport airport1 = Optional.ofNullable(airportRepository.findByIata(iata1.toUpperCase()))
                .orElseThrow(() -> new AirportNotFoundException(iata1));

        Airport airport2 = Optional.of(airportRepository.findByIata(iata2.toUpperCase()))
                .orElseThrow(() -> new AirportNotFoundException(iata2));

        StringBuilder sb = new StringBuilder();
        sb.append("Calculating flight duration between ")
                .append(airport1.getName()).append(" [")
                .append(airport1.getCountry()).append("] ")
                .append("and ")
                .append(airport2.getName()).append(" [")
                .append(airport2.getCountry()).append("]");

        log.info(sb.toString());

        BigDecimal distance = calculateDistanceBetweenAirportsInKm(airport1.getIata(), airport2.getIata());

        double flightDurationHours = Double.valueOf(distance.doubleValue()) / 809.4655d;

        return BigDecimal.valueOf(flightDurationHours).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public List<AirportDTO> fetchAllAirports() {

        List<String> countries = countryService.findAllCountries();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", key);
        headers.set("x-rapidapi-host", host);

        countries.forEach(country -> {

            StringBuilder sb = new StringBuilder();
            String url = sb.append(airports).append(country).toString();

            ResponseEntity<String> response = restTemplate.exchange(url,
                    HttpMethod.GET, new HttpEntity<>(headers), String.class);
            log.info("Response: {}", response.getBody());

            log.info("Getting country data...");
            ObjectMapper mapper = new ObjectMapper();

            try {

                JsonNode rootNode = mapper.readTree(response.getBody());
                JsonNode dataNode = rootNode.path("data");

                for (JsonNode airportNode : dataNode) {
                    AirportRestResponse airportRestResponse = new AirportRestResponse();
                    airportRestResponse.setName(airportNode.path("name").asText());
                    airportRestResponse.setIata(airportNode.path("iata").asText());
                    airportRestResponse.setIcao(airportNode.path("iaco").asText());
                    airportRestResponse.setLatitude(airportNode.path("latitude").asDouble());
                    airportRestResponse.setLongitude(airportNode.path("longitude").asDouble());
                    airportRestResponse.setCountry(airportNode.path("country").asText());
                    airportRestResponse.setRegion(airportNode.path("region").asText());
                    airportRestResponse.setTimezone(airportNode.path("timezone").asText());
                    airportRestResponse.setWebsite(airportNode.path("website").asText());
                    airportRestResponse.setWikipedia(airportNode.path("wikipedia").asText());

                    airportRepository.save(airportMapper.mapRestResponseToEntity(airportRestResponse));
                }

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return airportMapper.mapToDTO(airportRepository.findAll());
    }

    private double haversineFormula(double deltaLat, double deltaLon, Airport airport1, Airport airport2) {

        return Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(Math.toRadians(airport1.getLatitude())) * Math.cos(Math.toRadians(airport2.getLatitude())) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
    }
}

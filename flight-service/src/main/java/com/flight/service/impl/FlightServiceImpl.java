package com.flight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.dto.CompanyDetails;
import com.flight.dto.FlightDto;
import com.flight.dto.FlightRequest;
import com.flight.entity.Passenger;
import com.flight.entity.TopicResponse;
import com.flight.kafka.*;
import com.flight.mapper.FlightMapper;
import com.flight.mapper.PassengerMapper;
import com.flight.reposirory.FlightRepository;
import com.flight.reposirory.TopicRepository;
import com.flight.service.FlightService;
import com.flight.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final TopicRepository topicRepository;
    @Value("${spring.kafka.topics.flight-requests}")
    private String topic;

    private final FlightRepository flightRepository;
    private final FlightKafkaProducer flightKafkaProducer;
    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final FlightMapper flightMapper;
    private final PassengerMapper passengerMapper;

    private final String AIRPORT_TOPIC = "airport-response";
    private final String PLANE_TOPIC = "plane-response";
    private final String COMPANY_TOPIC = "company-response";
    private static final int EARTH_RADIUS = 6371;

    private CompletableFuture<TopicResponse> airportResponseFuture;
    private CompletableFuture<TopicResponse> planeResponseFuture;
    private CompletableFuture<TopicResponse> companyResponseFuture;


    @Override
    @Transactional
    public FlightDto createFlight(FlightRequest flightRequest) {

        airportResponseFuture = new CompletableFuture<>();
        planeResponseFuture = new CompletableFuture<>();
        companyResponseFuture = new CompletableFuture<>();

        flightKafkaProducer.sendFlightDetails(topic, flightRequest);

        TopicResponse airportString = airportResponseFuture.join(); // Join will wait for the response
        TopicResponse planeString = planeResponseFuture.join();
        TopicResponse companyString = companyResponseFuture.join();

//        TopicResponse airportString = topicService.findByFlightCodeAndTopic(flightRequest.getFlightCode(), AIRPORT_TOPIC);
//        TopicResponse planeString = topicService.findByFlightCodeAndTopic(flightRequest.getFlightCode(), PLANE_TOPIC);

        AirportApiResponse airportResponse;
        PlaneResponse planeResponse;
        CompanyResponse companyResponse;
        try {
            airportResponse = objectMapper.readValue(airportString.getResponse(), AirportApiResponse.class);
            planeResponse = objectMapper.readValue(planeString.getResponse(), PlaneResponse.class);
            companyResponse = objectMapper.readValue(companyString.getResponse(), CompanyResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (airportResponse != null && planeResponse != null) {

            FlightDto flightDto = createFlightResponseAssembler(planeResponse, airportResponse, companyResponse, flightRequest);
            flightRepository.save(flightMapper.mapToEntity(flightDto));

            return flightDto;
        }

        throw new RuntimeException();

    }

    @KafkaListener(topics = AIRPORT_TOPIC, groupId = "airport-service-consumer-group")
    public void listenAirportResponse(String response) {

        System.out.println("Received airport response: " + response);

        try {
            AirportResponse airportResponse = objectMapper.readValue(response, AirportResponse.class);
            System.out.println("");

            TopicResponse airportTopicResponse = new TopicResponse();
            airportTopicResponse.setFlightCode(airportResponse.getFlightCode());
            airportTopicResponse.setTopic(AIRPORT_TOPIC);
            airportTopicResponse.setResponse(response);

            topicRepository.save(airportTopicResponse);
            airportResponseFuture.complete(airportTopicResponse);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = PLANE_TOPIC, groupId = "plane-service-consumer-group")
    public void listenPlaneResponse(String response) {
        System.out.println("Received plane response: " + response);

        try {
            PlaneResponse planeResponse = objectMapper.readValue(response, PlaneResponse.class);

            TopicResponse planeTopicResponse = new TopicResponse();
            planeTopicResponse.setFlightCode(planeResponse.getFlightCode());
            planeTopicResponse.setTopic(PLANE_TOPIC);
            planeTopicResponse.setResponse(response);

            topicRepository.save(planeTopicResponse);
            planeResponseFuture.complete(planeTopicResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @KafkaListener(topics = COMPANY_TOPIC, groupId = "company-service-consumer-group")
    public void listenCompanyResponse(String response) {
        System.out.println("Received company response: " + response);

        try {
            CompanyResponse companyResponse = objectMapper.readValue(response, CompanyResponse.class);

            TopicResponse companyTopicResponse = new TopicResponse();
            companyTopicResponse.setFlightCode(companyResponse.getFlightCode());
            companyTopicResponse.setTopic(COMPANY_TOPIC);
            companyTopicResponse.setResponse(response);

            topicRepository.save(companyTopicResponse);
            companyResponseFuture.complete(companyTopicResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private BigDecimal calculateDistanceBetweenAirportsInKm(Airport departure, Airport arrival) {

        StringBuilder sb = new StringBuilder();
        sb.append("Calculating distance between ")
                .append(departure.getName()).append(" [")
                .append(departure.getCountry()).append("] ")
                .append("and ")
                .append(arrival.getName()).append(" [")
                .append(arrival.getCountry()).append("]");

        log.info(sb.toString());

        double deltaLat = Math.toRadians(arrival.getLatitude() - departure.getLatitude());
        double deltaLon = Math.toRadians(arrival.getLongitude() - departure.getLongitude());

        double a = haversineFormula(deltaLat, deltaLon, departure, arrival);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return BigDecimal.valueOf(EARTH_RADIUS * c)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFlightDuration(Airport departure, Airport arrival, int averagePlaneSpeed) {

        StringBuilder sb = new StringBuilder();
        sb.append("Calculating flight duration between ")
                .append(departure.getName()).append(" [")
                .append(departure.getCountry()).append("] ")
                .append("and ")
                .append(arrival.getName()).append(" [")
                .append(arrival.getCountry()).append("]");

        log.info(sb.toString());

        BigDecimal distance = calculateDistanceBetweenAirportsInKm(departure, arrival);

        double flightDurationHours = Double.valueOf(distance.doubleValue()) / averagePlaneSpeed;

        double durationInMinutes = flightDurationHours * 60;

        return BigDecimal.valueOf(durationInMinutes).setScale(2, RoundingMode.HALF_UP);
    }

    private double haversineFormula(double deltaLat, double deltaLon, Airport airport1, Airport airport2) {

        return Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(Math.toRadians(airport1.getLatitude())) * Math.cos(Math.toRadians(airport2.getLatitude())) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
    }

    private FlightDto createFlightResponseAssembler(PlaneResponse planeResponse,
                                                    AirportApiResponse airportResponse,
                                                    CompanyResponse companyResponse,
                                                    FlightRequest flightRequest) {

        FlightDto flightDto = new FlightDto();

        int averagePlaneSpeed = Integer.parseInt(planeResponse.getAverageSpeed());

        flightDto.setPlaneModel(planeResponse.getPlaneModel());
        flightDto.setDepartureAirport(airportResponse.getDepartureAirport().getName());
        flightDto.setArrivalAirport(airportResponse.getArrivalAirport().getName());
        flightDto.setCreatedAt(new Date());

        if (!flightRequest.getDepartureTime().before(flightDto.getCreatedAt())) {
            flightDto.setDepartureTime(flightRequest.getDepartureTime());
        } else {
            throw new IllegalArgumentException("Departure time must be in the future!");
        }

        CompanyDetails companyDetails = CompanyDetails.builder()
                .name(companyResponse.getName())
                .yearFounded(companyResponse.getYearFounded())
                .country(companyResponse.getCountry())
                .iataCode(companyResponse.getIataCode())
                .icaoCode(companyResponse.getIcaoCode())
                .fleetSize(companyResponse.getFleetSize())
                .headquarters(companyResponse.getHeadquarters())
                .destinations(companyResponse.getDestinations())
                .numberOfEmployees(companyResponse.getNumberOfEmployees())
                .website(companyResponse.getWebsite())
                .contactInfo(companyResponse.getContactInfo())
                .build();

        flightDto.setCompany(companyDetails);
        flightDto.setAveragePlaneSpeed(averagePlaneSpeed);

        flightDto.setFlightDistance(calculateDistanceBetweenAirportsInKm(
                airportResponse.getDepartureAirport(),
                airportResponse.getArrivalAirport()));

        flightDto.setFlightDuration(calculateFlightDuration(
                airportResponse.getDepartureAirport(),
                airportResponse.getArrivalAirport(),
                averagePlaneSpeed));

        flightDto.setPassenger(
                passengerMapper.mapToDTO(
                        Passenger.builder()
                                .firstName(flightRequest.getPassenger().getFirstName())
                                .lastName(flightRequest.getPassenger().getLastName())
                                .title(flightRequest.getPassenger().getTitle())
                                .gender(flightRequest.getPassenger().getGender())
                                .age(flightRequest.getPassenger().getAge())
                                .build()));


        flightDto.setArrivalTime(calculateArrivalTime(flightDto.getDepartureTime(), flightDto.getFlightDuration()));

        return flightDto;
    }

    private Date calculateArrivalTime(Date departure, BigDecimal flightDurationInMinutes) {

        int minutesToAdd = flightDurationInMinutes.intValue();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(departure);
        calendar.add(Calendar.MINUTE, minutesToAdd);

        return calendar.getTime();
    }

}

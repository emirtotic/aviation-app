package com.flight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.dto.CompanyDetails;
import com.flight.dto.FlightDto;
import com.flight.dto.FlightRequest;
import com.flight.entity.FlightResponseForReport;
import com.flight.entity.Passenger;
import com.flight.entity.TopicResponse;
import com.flight.exception.FlightProcessingException;
import com.flight.kafka.*;
import com.flight.mapper.FlightMapper;
import com.flight.mapper.PassengerMapper;
import com.flight.reposirory.FlightRepository;
import com.flight.service.FlightResponseForReportService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    @Value("${spring.kafka.topics.flight-requests}")
    private String flightTopic;
    @Value("${spring.kafka.topics.report-requests}")
    private String reportTopic;

    private final FlightRepository flightRepository;
    private final FlightKafkaProducer flightKafkaProducer;
    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final FlightMapper flightMapper;
    private final PassengerMapper passengerMapper;
    private final FlightResponseForReportService flightResponseForReportService;

    private final String AIRPORT_TOPIC = "airport-response";
    private final String PLANE_TOPIC = "plane-response";
    private final String COMPANY_TOPIC = "company-response";
    private final String AVIATION_EVENT_TOPIC = "aviation-event-response";
    private static final int EARTH_RADIUS = 6371;

    private CompletableFuture<TopicResponse> airportResponseFuture;
    private CompletableFuture<TopicResponse> planeResponseFuture;
    private CompletableFuture<TopicResponse> companyResponseFuture;
    private CompletableFuture<TopicResponse> aviationEventResponseFuture;


    @Override
    @Transactional
    public FlightDto createFlight(FlightRequest flightRequest) {

        airportResponseFuture = new CompletableFuture<>();
        planeResponseFuture = new CompletableFuture<>();
        companyResponseFuture = new CompletableFuture<>();
        aviationEventResponseFuture = new CompletableFuture<>();

        flightKafkaProducer.sendFlightDetails(flightTopic, flightRequest);

        TopicResponse airportString = airportResponseFuture.join(); // Join will wait for the response
        TopicResponse planeString = planeResponseFuture.join();
        TopicResponse companyString = companyResponseFuture.join();
        TopicResponse aviationEventString = aviationEventResponseFuture.join();

        AirportApiResponse airportResponse;
        PlaneResponse planeResponse;
        CompanyResponse companyResponse;
        AviationEventResponse aviationEventResponse;
        try {
            airportResponse = objectMapper.readValue(airportString.getResponse(), AirportApiResponse.class);
            planeResponse = objectMapper.readValue(planeString.getResponse(), PlaneResponse.class);
            companyResponse = objectMapper.readValue(companyString.getResponse(), CompanyResponse.class);
            aviationEventResponse = objectMapper.readValue(aviationEventString.getResponse(), AviationEventResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (airportResponse != null && planeResponse != null) {

            FlightDto flightDto = createFlightResponseAssembler(planeResponse,
                    airportResponse,
                    companyResponse,
                    flightRequest,
                    aviationEventResponse);
            flightRepository.save(flightMapper.mapToEntity(flightDto));

            FlightResponseForReport flightForReport = makeFlightReportObjectFromDto(flightDto, flightRequest);
            flightResponseForReportService.saveFlightResponseForReport(flightForReport);

            FlightResponseForReport reportDetails = flightResponseForReportService.findById(flightForReport.getId());

            flightKafkaProducer.sendReportDetails(reportTopic, reportDetails);

            return flightDto;
        }

        throw new FlightProcessingException("Required responses from airport and plane are missing!");

    }

    @KafkaListener(topics = AIRPORT_TOPIC, groupId = "airport-service-consumer-group")
    public void listenAirportResponse(String response) {

        log.info("Received airport response: {}", response);

        try {
            AirportResponse airportResponse = objectMapper.readValue(response, AirportResponse.class);

            TopicResponse airportTopicResponse = new TopicResponse();
            airportTopicResponse.setFlightCode(airportResponse.getFlightCode());
            airportTopicResponse.setTopic(AIRPORT_TOPIC);
            airportTopicResponse.setResponse(response);

            topicService.save(airportTopicResponse);
            airportResponseFuture.complete(airportTopicResponse);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = PLANE_TOPIC, groupId = "plane-service-consumer-group")
    public void listenPlaneResponse(String response) {

        log.info("Received plane response: {}", response);

        try {
            PlaneResponse planeResponse = objectMapper.readValue(response, PlaneResponse.class);

            TopicResponse planeTopicResponse = new TopicResponse();
            planeTopicResponse.setFlightCode(planeResponse.getFlightCode());
            planeTopicResponse.setTopic(PLANE_TOPIC);
            planeTopicResponse.setResponse(response);

            topicService.save(planeTopicResponse);
            planeResponseFuture.complete(planeTopicResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @KafkaListener(topics = COMPANY_TOPIC, groupId = "company-service-consumer-group")
    public void listenCompanyResponse(String response) {

        log.info("Received company response: {}", response);

        try {
            CompanyResponse companyResponse = objectMapper.readValue(response, CompanyResponse.class);

            TopicResponse companyTopicResponse = new TopicResponse();
            companyTopicResponse.setFlightCode(companyResponse.getFlightCode());
            companyTopicResponse.setTopic(COMPANY_TOPIC);
            companyTopicResponse.setResponse(response);

            topicService.save(companyTopicResponse);
            companyResponseFuture.complete(companyTopicResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @KafkaListener(topics = AVIATION_EVENT_TOPIC, groupId = "aviation-event-service-consumer-group")
    public void listenAviationEventsResponse(String response) {

        log.info("Received Aviation Event response: {}", response);

        try {
            AviationEventResponse aviationEventResponse = objectMapper.readValue(response, AviationEventResponse.class);

            TopicResponse aviationEventTopicResponse = new TopicResponse();
            aviationEventTopicResponse.setFlightCode(aviationEventResponse.getFlightCode());
            aviationEventTopicResponse.setTopic(AVIATION_EVENT_TOPIC);
            aviationEventTopicResponse.setResponse(response);

            topicService.save(aviationEventTopicResponse);
            aviationEventResponseFuture.complete(aviationEventTopicResponse);
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
                                                    FlightRequest flightRequest,
                                                    AviationEventResponse aviationEventResponse) {

        FlightDto flightDto = new FlightDto();

        int averagePlaneSpeed = Integer.parseInt(planeResponse.getAverageSpeed());

        flightDto.setPlaneModel(planeResponse.getPlaneModel());
        flightDto.setDepartureAirport(airportResponse.getDepartureAirport().getName());
        flightDto.setDepartureAirportIata(airportResponse.getDepartureAirport().getIata());
        flightDto.setDepartureAirportCountry(airportResponse.getDepartureAirport().getCountry());
        flightDto.setArrivalAirport(airportResponse.getArrivalAirport().getName());
        flightDto.setArrivalAirportIata(airportResponse.getArrivalAirport().getIata());
        flightDto.setArrivalAirportCountry(airportResponse.getArrivalAirport().getCountry());
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

        flightDto.setFlightDistanceKm(calculateDistanceBetweenAirportsInKm(
                airportResponse.getDepartureAirport(),
                airportResponse.getArrivalAirport()));

        flightDto.setFlightDurationInMinutes(calculateFlightDuration(
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


        flightDto.setArrivalTime(calculateArrivalTime(flightDto.getDepartureTime(), flightDto.getFlightDurationInMinutes()));

        flightDto.setEvents(aviationEventResponse.getEvents());
        return flightDto;
    }

    private Date calculateArrivalTime(Date departure, BigDecimal flightDurationInMinutes) {

        int minutesToAdd = flightDurationInMinutes.intValue();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(departure);
        calendar.add(Calendar.MINUTE, minutesToAdd);

        return calendar.getTime();
    }

    private FlightResponseForReport makeFlightReportObjectFromDto(FlightDto flightDto, FlightRequest flightRequest) {
        FlightResponseForReport ffr = new FlightResponseForReport();
        try {
            ffr.setResponse(objectMapper.writeValueAsString(flightDto));
            ffr.setFlightCode(flightRequest.getFlightCode());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ffr;
    }
}
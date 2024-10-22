package com.airport.kafka;
import com.airport.dto.AirportDTO;
import com.airport.service.AirportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final AirportService airportService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "flight-requests", groupId = "airport-service-consumer-group")
    public void listen(String flightRequestJson) {
        try {
            FlightApiRequest flightApiRequest = objectMapper.readValue(flightRequestJson, FlightApiRequest.class);
            System.out.println("Received flight request for airport: " + flightApiRequest.getDepartureAirport());

            AirportDTO departure = airportService.findAirportByIata(flightApiRequest.getDepartureAirport());
            AirportDTO arrival = airportService.findAirportByIata(flightApiRequest.getArrivalAirport());

            FlightApiResponse flightApiResponse = new FlightApiResponse();
            flightApiResponse.setFlightCode(flightApiRequest.getFlightCode());
            flightApiResponse.setArrivalAirport(objectMapper.writeValueAsString(arrival));
            flightApiResponse.setDepartureAirport(objectMapper.writeValueAsString(departure));

            kafkaTemplate.send("airport-response", objectMapper.writeValueAsString(flightApiResponse));
            log.info("Sent to Flight service " + objectMapper.writeValueAsString(flightApiResponse));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


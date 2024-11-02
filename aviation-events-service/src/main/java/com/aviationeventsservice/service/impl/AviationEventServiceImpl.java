package com.aviationeventsservice.service.impl;

import com.aviationeventsservice.dto.FlightApiRequest;
import com.aviationeventsservice.dto.FlightApiResponse;
import com.aviationeventsservice.entity.AviationEvent;
import com.aviationeventsservice.repository.AviationEventRepository;
import com.aviationeventsservice.service.AviationEventFetcherService;
import com.aviationeventsservice.service.AviationEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AviationEventServiceImpl implements AviationEventService {

    private final AviationEventRepository repository;
    private final AviationEventFetcherService fetcherService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @Override
    @KafkaListener(topics = "flight-requests", groupId = "aviation-event-service-consumer-group")
    public String fetchAviationEvents(String flightRequestJson) {

        FlightApiRequest flightApiRequest;
        FlightApiResponse flightApiResponse = new FlightApiResponse();

        try {

            flightApiRequest = objectMapper.readValue(flightRequestJson, FlightApiRequest.class);

            flightApiResponse.setFlightCode(flightApiRequest.getFlightCode());

            String requestDate = fetcherService.convertDateToDDMM(flightApiRequest.getDepartureTime().toString());

            AviationEvent dbResponse = repository.findByDate(requestDate);

            if (dbResponse == null) {
                log.info("No data fetched from db. Redirecting to API...");
                String response = fetcherService.fetchAviationEvents(requestDate);
                AviationEvent newEvent = new AviationEvent();
                newEvent.setDate(requestDate);
                newEvent.setEvents(response);
                repository.save(newEvent);

                flightApiResponse.setEvents(response);
                kafkaTemplate.send("aviation-event-response", objectMapper.writeValueAsString(flightApiResponse));

                return response;

            }

            flightApiResponse.setEvents(dbResponse.getEvents());
            kafkaTemplate.send("aviation-event-response", objectMapper.writeValueAsString(flightApiResponse));

            return dbResponse.getEvents();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

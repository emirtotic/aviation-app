package com.plane.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plane.dto.PlaneDTO;
import com.plane.entity.Plane;
import com.plane.service.PlaneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaneKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final PlaneService planeService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "flight-requests", groupId = "plane-service-consumer-group")
    public void listen(String flightRequestJson) {

        try {
            FlightApiRequest flightApiRequest = objectMapper.readValue(flightRequestJson, FlightApiRequest.class);

            PlaneDTO planeDTO = planeService.findPlaneById(flightApiRequest.getPlaneId());

            FlightApiResponse flightApiResponse = FlightApiResponse
                    .builder()
                    .flightCode(flightApiRequest.getFlightCode())
                    .model(planeDTO.getModel())
                    .capacity(planeDTO.getCapacity())
                    .type(planeDTO.getType().name())
                    .description(planeDTO.getDescription())
                    .averageSpeed(planeDTO.getAverageSpeed())
                    .build();

            System.out.println("PLANE = POZVANO");

            kafkaTemplate.send("plane-response", objectMapper.writeValueAsString(flightApiResponse));
            log.info("Sent to Flight service " + objectMapper.writeValueAsString(flightApiResponse));


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}


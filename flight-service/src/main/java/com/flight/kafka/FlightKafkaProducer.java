package com.flight.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.dto.FlightRequest;
import com.flight.entity.FlightResponseForReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlightKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public FlightKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendFlightDetails(String topic, FlightRequest flightRequest) {
        try {
            String flightRequestJson = objectMapper.writeValueAsString(flightRequest);
            kafkaTemplate.send(topic, flightRequestJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendReportDetails(String topic, FlightResponseForReport flightResponseForReport) {
        try {
            String reportDetails = objectMapper.writeValueAsString(flightResponseForReport);
            kafkaTemplate.send(topic, reportDetails);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}


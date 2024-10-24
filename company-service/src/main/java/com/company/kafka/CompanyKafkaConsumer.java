package com.company.kafka;

import com.company.dto.CompanyDTO;
import com.company.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final CompanyService companyService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "flight-requests", groupId = "company-service-consumer-group")
    public void listen(String flightRequestJson) {

        try {
            FlightApiRequest flightApiRequest = objectMapper.readValue(flightRequestJson, FlightApiRequest.class);

            CompanyDTO companyDTO = companyService.findCompanyById(flightApiRequest.getCompanyId());

            FlightApiResponse flightApiResponse = FlightApiResponse
                    .builder()
                    .flightCode(flightApiRequest.getFlightCode())
                    .name(companyDTO.getName())
                    .yearFounded(companyDTO.getYearFounded())
                    .country(companyDTO.getCountry())
                    .iataCode(companyDTO.getIataCode())
                    .icaoCode(companyDTO.getIcaoCode())
                    .fleetSize(companyDTO.getFleetSize())
                    .headquarters(companyDTO.getHeadquarters())
                    .destinations(companyDTO.getDestinations())
                    .numberOfEmployees(companyDTO.getNumberOfEmployees())
                    .website(companyDTO.getWebsite())
                    .contactInfo(companyDTO.getContactInfo())
                    .build();

            System.out.println("COMPANY = POZVANO");

            kafkaTemplate.send("company-response", objectMapper.writeValueAsString(flightApiResponse));
            log.info("Sent to Flight service " + objectMapper.writeValueAsString(flightApiResponse));


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


}

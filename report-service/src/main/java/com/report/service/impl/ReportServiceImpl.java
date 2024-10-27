package com.report.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.entity.FlightReport;
import com.report.service.JasperService;
import com.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final JasperService jasperService;
    private final ObjectMapper objectMapper;
    private static final String REPORT_DIRECTORY = "/Users/emirtotic/Documents/projects/aviation-app/report-service/src/main/resources/flight-reports";


    private final String REPORT_TOPIC = "report-requests";


    @Override
    public byte[] createPdfReport(FlightReport flightReport) {
        Map<String, Object> params = new HashMap<>();
        params.put("flightReport", flightReport);

        try (InputStream inputStream = jasperService.exportReportToPDF("/report/flight-report.jrxml", params, Collections.singletonList(flightReport));
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }


    @KafkaListener(topics = REPORT_TOPIC, groupId = "report-service-consumer-group")
    public FlightReport listenReportResponse(String message) {
        log.info("Received flight details: {}", message);
        FlightReport flightReport;

        try {
            JsonNode rootNode = objectMapper.readTree(message);
            String responseJson = rootNode.get("response").asText();
            flightReport = objectMapper.readValue(responseJson, FlightReport.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing FlightReport", e);
        }

        StringBuilder fileName = new StringBuilder();
        fileName.append("FlightReport - ")
                        .append(flightReport.getPassenger().getTitle()).append(" ")
                        .append(flightReport.getPassenger().getFirstName()).append(" ")
                        .append(flightReport.getPassenger().getLastName()).append(" ")
                        .append(".pdf");


        savePdfReport(flightReport, fileName.toString());
        return flightReport;
    }


    public void savePdfReport(FlightReport flightReport, String fileName) {
        byte[] pdfData = createPdfReport(flightReport);

        Path filePath = Paths.get(REPORT_DIRECTORY, fileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
            fileOutputStream.write(pdfData);
            log.info("PDF report saved to: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Error saving PDF report", e);
        }
    }

}

package com.flight.service.impl;

import com.flight.entity.FlightResponseForReport;
import com.flight.entity.TopicResponse;
import com.flight.exception.FlightForReportNotFoundException;
import com.flight.reposirory.FlightResponseForReportRepository;
import com.flight.reposirory.TopicRepository;
import com.flight.service.FlightResponseForReportService;
import com.flight.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightResponseForReportServiceImpl implements FlightResponseForReportService {

    private final FlightResponseForReportRepository repository;

    @Override
    public FlightResponseForReport findByFlightCode(String flightCode) {

        FlightResponseForReport byFlightCode = Optional.ofNullable(
                repository.findByFlightCode(flightCode)
        ).orElseThrow(() -> new FlightForReportNotFoundException(flightCode));

        return byFlightCode;
    }

    @Override
    public FlightResponseForReport findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new FlightForReportNotFoundException(id));
    }

    @Override
    public void saveFlightResponseForReport(FlightResponseForReport flightResponseForReport) {
        repository.save(flightResponseForReport);
    }
}

package com.flight.reposirory;

import com.flight.entity.FlightResponseForReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightResponseForReportRepository extends JpaRepository<FlightResponseForReport, Long> {

    FlightResponseForReport findByFlightCode(String flightCode);

}

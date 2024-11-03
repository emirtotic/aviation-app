package com.report.service;

import com.report.entity.FlightReport;

public interface ReportService {

    byte[] createPdfReport(FlightReport flightReport);
}
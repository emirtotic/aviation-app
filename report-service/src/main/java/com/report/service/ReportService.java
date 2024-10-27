package com.report.service;

import com.report.entity.FlightReport;

import java.io.InputStream;

public interface ReportService {

    byte[] createPdfReport(FlightReport flightReport);
}
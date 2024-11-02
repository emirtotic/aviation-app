package com.aviationeventsservice.service;

public interface AviationEventFetcherService {

    String fetchAviationEvents(String date);
    String convertDateToDDMM(String dateStr);
}

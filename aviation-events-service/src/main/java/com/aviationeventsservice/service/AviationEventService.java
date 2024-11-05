package com.aviationeventsservice.service;

public interface AviationEventService {

    String fetchAviationEventsLocally(String date);
    String fetchAviationEvents(String date);
}

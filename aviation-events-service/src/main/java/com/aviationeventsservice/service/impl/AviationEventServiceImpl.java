package com.aviationeventsservice.service.impl;

import com.aviationeventsservice.entity.AviationEvent;
import com.aviationeventsservice.repository.AviationEventRepository;
import com.aviationeventsservice.service.AviationEventFetcherService;
import com.aviationeventsservice.service.AviationEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AviationEventServiceImpl implements AviationEventService {

    private final AviationEventRepository repository;
    private final AviationEventFetcherService fetcherService;


    @Override
    public String fetchAviationEvents(String date) {

        if (date.length() != 4) {
            log.error("Date format is invalid. It should be 4 characters long.");
        }

        AviationEvent dbResponse = repository.findByDate(date);

        if (dbResponse == null) {
            log.info("No data fetched from db. Redirecting to API...");

            String response = fetcherService.fetchAviationEvents(date);
            AviationEvent newEvent = new AviationEvent();
            newEvent.setDate(date);
            newEvent.setEvents(response);
            repository.save(newEvent);

            return response;

        }

        return dbResponse.getEvents();
    }
}

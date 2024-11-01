package com.aviationeventsservice.controller;

import com.aviationeventsservice.service.AviationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aviation-events")
public class AviationEventServiceController {

    private final AviationEventService aviationEventService;

    @PostMapping("{date}")
    public ResponseEntity<String> findAllEventsForDate(@RequestParam(name = "date") String date) {
        return null;
    }

}

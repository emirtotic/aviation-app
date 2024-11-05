package com.aviationeventsservice.controller;

import com.aviationeventsservice.service.AviationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aviation-events")
public class AviationEventServiceController {

    private final AviationEventService aviationEventService;

    @GetMapping("/date")
    public ResponseEntity<String> getAviationEvents(@RequestParam String date) {

        String response = aviationEventService.fetchAviationEventsLocally(date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

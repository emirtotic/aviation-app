package com.aviationeventsservice.controller;

import com.aviationeventsservice.service.AviationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aviation-events")
public class AviationEventServiceController {

    private final AviationEventService aviationEventService;

    @GetMapping("/date")
    public ResponseEntity<String> getAviationEvents(@RequestParam String date) {

        String response = aviationEventService.fetchAviationEvents(date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

package com.flight.service;

import com.flight.entity.TopicResponse;

public interface TopicService {

    TopicResponse findByFlightCode(String flightCode);
    TopicResponse findByFlightCodeAndTopic(String flightCode, String topic);

}

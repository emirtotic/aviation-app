package com.flight.service.impl;

import com.flight.entity.TopicResponse;
import com.flight.reposirory.TopicRepository;
import com.flight.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public TopicResponse findByFlightCode(String flightCode) {
        return topicRepository.findByFlightCode(flightCode);
    }

    @Override
    public TopicResponse findByFlightCodeAndTopic(String flightCode, String topic) {
        return topicRepository.findByFlightCodeAndTopic(flightCode, topic);
    }
}

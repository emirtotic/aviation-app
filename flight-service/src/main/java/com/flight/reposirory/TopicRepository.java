package com.flight.reposirory;

import com.flight.entity.TopicResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicResponse, Long> {

    TopicResponse findByFlightCode(String flightCode);
    TopicResponse findByFlightCodeAndTopic(String flightCode, String topic);

}

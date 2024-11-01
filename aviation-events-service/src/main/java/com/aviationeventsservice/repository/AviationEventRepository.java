package com.aviationeventsservice.repository;

import com.aviationeventsservice.entity.AviationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AviationEventRepository extends JpaRepository<AviationEvent, Long> {

    AviationEvent findByDate(String date);

}

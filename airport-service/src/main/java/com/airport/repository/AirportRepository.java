package com.airport.repository;

import com.airport.entity.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirportRepository extends MongoRepository<Airport, String> {

    Airport findByIata(String iata);

}

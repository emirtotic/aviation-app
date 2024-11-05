package com.airport.repository;

import com.airport.entity.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AirportRepository extends MongoRepository<Airport, String> {

    Airport findByIata(String iata);
    List<Airport> findAllByCountry(String country);

}

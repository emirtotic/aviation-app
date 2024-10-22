package com.flight.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AirportDeserializer extends JsonDeserializer<Airport> {

    @Override
    public Airport deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        String airportJson = jsonParser.getText();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(airportJson, Airport.class);
    }
}


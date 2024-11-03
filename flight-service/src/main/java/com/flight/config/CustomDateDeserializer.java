package com.flight.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom deserializer for Date validation. Check if the date exist.
 */
public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        String date = jsonParser.getText();

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new IOException("Invalid date format provided for date: " + date);
        }
    }
}


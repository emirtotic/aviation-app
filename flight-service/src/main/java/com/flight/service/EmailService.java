package com.flight.service;

public interface EmailService {

    void sendFlightCreationEmail(String toEmail, String subject, String body);
}

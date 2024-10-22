CREATE SCHEMA IF NOT EXISTS flights;

CREATE TABLE IF NOT EXISTS flights.flight
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    plane_id          BIGINT       NOT NULL,
    departure_airport VARCHAR(3)   NOT NULL,
    arrival_airport   VARCHAR(3)   NOT NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    departure_time    TIMESTAMP    NOT NULL,
    arrival_time      TIMESTAMP    NOT NULL,
    company           VARCHAR(255) NOT NULL
);



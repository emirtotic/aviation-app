CREATE TABLE IF NOT EXISTS flight.passenger
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    title      VARCHAR(10)  NOT NULL,
    gender     VARCHAR(10)  NOT NULL,
    age        INT          NOT NULL,
    flight_id  BIGINT       NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flight.flight (id) ON DELETE CASCADE
);


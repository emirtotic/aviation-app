CREATE TABLE IF NOT EXISTS flights.flight_response_for_report
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    flightCode VARCHAR(30)                                           NOT NULL,
    response   TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL                                       NOT NULL
);


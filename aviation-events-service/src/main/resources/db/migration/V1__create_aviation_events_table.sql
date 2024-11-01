CREATE TABLE IF NOT EXISTS events
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    date   VARCHAR(100) UNIQUE                                   NOT NULL,
    events TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
);

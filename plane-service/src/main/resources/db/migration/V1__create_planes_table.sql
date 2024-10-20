CREATE TABLE IF NOT EXISTS plane
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    model       VARCHAR(100)                   NOT NULL,
    capacity    INT                            NOT NULL,
    type        ENUM ('PASSENGER', 'REGIONAL') NOT NULL,
    description VARCHAR(255)
);

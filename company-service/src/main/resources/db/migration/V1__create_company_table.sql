CREATE SCHEMA IF NOT EXISTS companies;

CREATE TABLE IF NOT EXISTS companies.company
(
    id                  BIGINT PRIMARY KEY,
    name                VARCHAR(60) NOT NULL,
    year_founded        INT         NOT NULL,
    country             VARCHAR(60) NOT NULL,
    iata_code           VARCHAR(5)  NOT NULL,
    icao_code           VARCHAR(5)  NOT NULL,
    fleet_size          INT,
    headquarters        VARCHAR(100),
    destinations        INT,
    number_of_employees INT,
    website             VARCHAR(255),
    contact_info        VARCHAR(255)
);


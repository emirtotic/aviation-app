<h1>Flight Management System</h1>

<p>This project is a microservice application for managing flight data, including details on aircraft, airports, airlines, and historical aviation events for a specific date. It uses modern Java backend technologies to offer high flexibility and scalability.</p>

<h2>Technologies and Skills Applied</h2>
<ul>
  <li><strong>Java 17</strong>: The primary programming language for implementing all services.</li>
  <li><strong>Spring Boot 2.7.4</strong>: The backbone of the microservice architecture with supporting modules:
    <ul>
      <li><strong>Spring Data JPA</strong> for working with the MySQL database.</li>
      <li><strong>Spring Web</strong> for creating RESTful API endpoints.</li>
    </ul>
  </li>
  <li><strong>MySQL and MongoDB</strong>: MySQL is used for storing data about aircraft and companies, while MongoDB stores airport information.</li>
  <li><strong>Kafka</strong>: Asynchronous communication between services for exchanging data across different microservices (flight-service, airport-service, plane-service, company-service, and aviation-events-service).</li>
  <li><strong>Eureka Server</strong>: For registering and discovering microservices.</li>
  <li><strong>Spring Cloud Gateway</strong>: Gateway server for centralized management of access to various services.</li>
  <li><strong>MapStruct</strong>: Used for object mapping within the code.</li>
  <li><strong>Flyway</strong>: Used for initializing data in the MySQL database at application startup.</li>
  <li><strong>Swagger</strong> (Springfox and Springdoc): API documentation for each microservice.</li>
  <li><strong>Unit Testing</strong>: JUnit 5 was used for test management, and Mockito was used for mocking dependencies.</li>
  <li><strong>Jsoup</strong>: Used for parsing HTML content and extracting historical events for a given date.</li>
  <li><strong>Jackson</strong>: For JSON data serialization and deserialization, with additional <code>@JsonFormat</code> configurations for date formats.</li>
  <li><strong>Lombok</strong>: Reduces boilerplate code in DTO classes by automatically generating getters and setters.</li>
</ul>

<h2>Microservice Structure</h2>
<ul>
  <li><strong>Plane Service</strong>: Collects and stores information about aircraft, including average plane speed, in a MySQL database.</li>
  <li><strong>Airport Service</strong>: Contains airport information, implemented with MongoDB, and allows for country-based searching. Data is integrated from an existing third-party API.</li>
  <li><strong>Company Service</strong>: Stores details about airlines and uses MySQL. It communicates asynchronously with the plane service to retrieve the company fleet’s aircraft.</li>
  <li><strong>Flight Service</strong>: Manages flight data and includes asynchronous calls to aviation-event-service to fetch historical events on the flight date, plane-service for aircraft data, company-service for company data, airport-service for departure and arrival airport information, and report-service to generate a PDF report on the created flight.</li>
  <li><strong>Aviation Event Service</strong>: Parses HTML from a specified website to obtain relevant aviation events for a particular date and sends information to a Kafka topic.</li>
  <li><strong>Report Service</strong>: Asynchronously collects the necessary data from the flight-service to create a Jasper report for the desired flight.</li>
</ul>

<h2>Key Features</h2>
<ul>
  <li><strong>Asynchronous Communication</strong>: Utilizes Kafka for efficient data exchange between various microservices.</li>
  <li><strong>Caffeine Cache</strong>: Optimizes performance and reduces database load.</li>
  <li><strong>Data Validation</strong>: Implemented format validation for dates in the FlightRequest DTO to prevent errors from incorrect dates.</li>
  <li><strong>Logging and Monitoring</strong>: Through Slf4j logging and Spring Boot Actuator to monitor service status.</li>
  <li><strong>Automated API Documentation</strong>: Swagger allows easy viewing and testing of all API endpoints.</li>
  <li><strong>Reporting</strong>: Generates customized reports on flights using JasperReports, including details on passengers, aircraft, airlines, and relevant historical events.</li>
</ul>

<h2>Conclusion</h2>
<p>This project is a robust example of a microservice application focusing on performance, scalability, and ease of maintenance. Using modern technologies and best practices, the application is built to work with complex aviation industry data and provides a stable foundation for further development and customization according to specific business needs.</p>
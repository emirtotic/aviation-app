package com.aviationeventsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AviationEventsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AviationEventsServiceApplication.class, args);
    }

}

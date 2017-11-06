package edu.shu.bowling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BowlingRegistrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BowlingRegistrationServiceApplication.class, args);
    }
}

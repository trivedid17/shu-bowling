package edu.shu.bowling.discovery.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BowlingEurekaDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowlingEurekaDiscoveryServiceApplication.class, args);
	}
}

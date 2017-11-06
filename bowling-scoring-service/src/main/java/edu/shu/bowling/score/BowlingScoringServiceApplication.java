package edu.shu.bowling.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BowlingScoringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowlingScoringServiceApplication.class, args);
	}
}

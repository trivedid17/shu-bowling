package edu.shu.bowling.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BowlingConfigServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(BowlingConfigServerApplication.class, args);
	}
}

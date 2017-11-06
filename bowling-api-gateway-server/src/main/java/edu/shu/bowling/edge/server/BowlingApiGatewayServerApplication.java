package edu.shu.bowling.edge.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@SpringBootApplication
@EnableZuulProxy
public class BowlingApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowlingApiGatewayServerApplication.class, args);
	}
}

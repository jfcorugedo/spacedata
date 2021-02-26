package com.jfcorugedo.spacedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class SpacedataApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpacedataApplication.class, args);
	}

}

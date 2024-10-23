package com.w2m.spaceships;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.w2m.spaceships","com.w2m.spaceships.repositories"})
@SpringBootApplication
public class SpaceshipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceshipsApplication.class, args);
	}

}

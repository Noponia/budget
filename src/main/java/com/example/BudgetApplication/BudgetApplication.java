package com.example.BudgetApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Class that contains the main method for the Spring Boot Application
 * 
 * @author Benjamin Chang
 *
 */
@SpringBootApplication
public class BudgetApplication {

	/**
	 * Entry point of the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BudgetApplication.class, args);
	}

	/**
	 * Bean method to get SpringApplicationContext
	 * 
	 * @return a new SpringApplicationContext
	 */
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
}

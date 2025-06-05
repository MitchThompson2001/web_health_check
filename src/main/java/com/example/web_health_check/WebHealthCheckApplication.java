/*
 * Name: Mitchell Thompson
 * File: WebHealthCheckApplication.java
 * Date: 6-5-2025
 * Project: Web Health Check
 * Description: Main application class for the Web Health Check application.
 * This application is built using Spring Boot and serves as a health check service.
 * It is designed to monitor the health of web services and provide status information.
 * The application can be run as a standalone service and is configured to start with the main method.
*/

package com.example.web_health_check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebHealthCheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebHealthCheckApplication.class, args);
	}

}

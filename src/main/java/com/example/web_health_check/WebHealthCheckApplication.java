/*
 * Name: Mitchell Thompson
 * File: WebHealthCheckApplication.java
 * Date: 6-5-2025
 * Project: Web Health Check
*/

package com.example.web_health_check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true) // Enable asynchronous processing
public class WebHealthCheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebHealthCheckApplication.class, args);
	}

}

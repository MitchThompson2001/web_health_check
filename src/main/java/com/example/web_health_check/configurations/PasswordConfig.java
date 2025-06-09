/*
 * Name: Mitchell Thompson
 * File: PasswordConfig.java
 * Project: Data Viewer Application
 */

package com.example.web_health_check.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * Configuration class that encodes passwords to be 
 * securely stored in a database
 */
@Configuration
public class PasswordConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

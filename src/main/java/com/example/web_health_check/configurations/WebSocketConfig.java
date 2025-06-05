/*
 * Name: Mitchell Thompson
 * File: WebSocketConfig.java
 * Date: 6-5-2025
 * Project: Web Health Check
*/

package com.example.web_health_check.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple message broker to handle messages prefixed with /topic
        config.enableSimpleBroker("/topic");
        // Specify the prefix for messages sent to the controller
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the WebSocket endpoint at /ws
        registry.addEndpoint("/ws")
            .setAllowedOrigins("http://localhost:8080")
            .withSockJS();
    }
}

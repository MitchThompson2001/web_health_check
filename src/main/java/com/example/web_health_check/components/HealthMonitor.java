/*
 * Name: Mitchell Thompson
 * File: HealthMonitor.java
 * Date: 6-5-2025
 * Project: Web Health Check
 * Description:
 */

package com.example.web_health_check.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HealthMonitor implements HealthIndicator {

    private final WebsiteMonitorProperties websiteMonitorProperties;
    private final RestTemplate restTemplate;

    public HealthMonitor(WebsiteMonitorProperties websiteMonitorProperties) {
        this.websiteMonitorProperties = websiteMonitorProperties;
        this.restTemplate = new RestTemplate();
    }

   @Override
    public Health health() {
        Map<String, Object> statuses = new HashMap<>();

        List<String> urls = websiteMonitorProperties.getUrls();
        if (urls == null || urls.isEmpty()) {
            return Health.unknown().withDetail("error", "No URLs configured").build();
        }
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        urls.forEach(url -> {
            CompletableFuture<Void> future = checkWebsiteHealth(url, statuses);
            futures.add(future);
        });

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        boolean allUp = statuses.values()
            .stream()
            .allMatch(status -> "UP".equals(status));
        return allUp ? Health.up().withDetails(statuses).build() : Health.down().withDetails(statuses).build();
    }

    @Async
    public CompletableFuture<Void> checkWebsiteHealth(String url, Map<String, Object> statuses) {
        try {
            String response = restTemplate.getForObject(url, String.class);
            statuses.put(url, response != null ? "UP" : "DOWN");
        } catch (Exception e) {
            statuses.put(url, "DOWN");
            System.err.println("Error checking health for " + url + ": " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }
}

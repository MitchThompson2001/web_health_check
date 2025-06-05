/*
 * Name: Mitchell Thompson
 * File: HealthMonitor.java
 * Date: 6-5-2025
 * Project: Web Health Check
 * Description:
 */

package com.example.web_health_check.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
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
        boolean allUp = true;

        List<String> urls = websiteMonitorProperties.getUrls();
        if (urls == null || urls.isEmpty()) {
            return Health.unknown().withDetail("error", "No URLs configured").build();
        }

        for (String url : urls) {
            try {
                String response = restTemplate.getForObject(url, String.class);
                statuses.put(url, response != null ? "UP" : "DOWN");
                if (response == null) allUp = false;
            } catch (Exception e) {
                allUp = false;
                statuses.put(url, "DOWN: " + e.getMessage());
            }
        }

        return allUp ?
            Health.up().withDetails(statuses).build() :
            Health.down().withDetails(statuses).build();
    }
}

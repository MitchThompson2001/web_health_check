package com.example.web_health_check.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;

@Service
public class HealthMonitorService {

    private final Map<String, Duration> timeUp;
    private LocalDateTime lastCheckedTime;

    public HealthMonitorService() {
        timeUp = new HashMap<>();
        timeUp.put("https://www.google.com", Duration.ZERO);
        timeUp.put("https://www.samsung.com", Duration.ZERO);
        timeUp.put("https://www.rotech.com", Duration.ZERO);
        timeUp.put("https://www.yelp.com", Duration.ZERO);
        timeUp.put("https://www.walmart.com", Duration.ZERO);
        timeUp.put("https://www.microsoft.com", Duration.ZERO);
        timeUp.put("https://www.mieleusa.com", Duration.ZERO);
        timeUp.put("https://www.bestbuy.com", Duration.ZERO);
        timeUp.put("https://www.microcenter.com", Duration.ZERO);
        timeUp.put("https://www.wikipedia.org", Duration.ZERO);
        timeUp.put("https://www.apple.com", Duration.ZERO);
        timeUp.put("https://www.baylor.edu", Duration.ZERO);
        timeUp.put("https://www.oracle.com", Duration.ZERO);

        this.lastCheckedTime = LocalDateTime.now();
    }

    public Map<String, String> fetchTimeUp(Health health) {

        Map<String, Object> details = health.getDetails();
        if (details != null && !details.isEmpty()) {
            for (Map.Entry<String, Object> entry : details.entrySet()) {
                String url = entry.getKey();
                String status = entry.getValue().toString();

                if (status.equals("UP")) {
                    Duration duration = timeUp.getOrDefault(url, Duration.ZERO);
                    duration = duration.plus(Duration.between(lastCheckedTime, LocalDateTime.now()));
                    timeUp.put(url, duration);
                } else {
                    timeUp.put(url, Duration.ZERO);
                }
            }
        }

        lastCheckedTime = LocalDateTime.now();
        return formatDuration(timeUp);
    }

    private Map<String, String> formatDuration(Map<String, Duration> durations) {
        Map<String, String> formattedDurations = new HashMap<>();
        for (Map.Entry<String, Duration> entry : durations.entrySet()) {
            String url = entry.getKey();
            Duration duration = entry.getValue();

            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;

            formattedDurations.put(url, String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }

        return formattedDurations;
    }
    
}

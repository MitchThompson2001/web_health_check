/*
 * Name: Mitchell Thompson
 * File: HealthMonitorController.java
 * Date: 6-5-2025
 * Project: Web Health Check
*/

package com.example.web_health_check.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.web_health_check.components.HealthMonitor;
import com.example.web_health_check.services.HealthMonitorService;

@Controller
public class HealthMonitorController {

    @Autowired private HealthMonitor healthMonitor;
    @Autowired private HealthMonitorService healthMonitorService;

    @Autowired SimpMessagingTemplate messagingTemplate;

    @GetMapping("/get_health_statuses")
    public String getHealthStatuses(Model model) {
        Health health = healthMonitor.health();
        Map<String, Object> details = health.getDetails();
        Map<String, String> timeUp = healthMonitorService.fetchTimeUp(health);

        List<Map<String, String>> websiteStatusData = new ArrayList<>();
        
        details.forEach((url, status) -> {
            String statusStr = status != null ? status.toString() : "N/A";
            String timeUpStr = timeUp.getOrDefault(url, "N/A");
            
            websiteStatusData.add(Map.of(
                "url", url,
                "status", statusStr,
                "timeUp", timeUpStr
            ));
        });

        System.out.println("Sending data: " + websiteStatusData); // Verify data is correct
        messagingTemplate.convertAndSend("/topic/healthStatuses", websiteStatusData);
        
        return "healthStatuses";
    }
}

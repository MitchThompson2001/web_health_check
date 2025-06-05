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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.web_health_check.components.HealthMonitor;
import com.example.web_health_check.services.HealthMonitorService;

@Controller
public class HealthMonitorController {

    @Autowired private HealthMonitor healthMonitor;
    @Autowired private HealthMonitorService healthMonitorService;

    @GetMapping("/get_health_statuses")
    public String getHealthStatuses(Model model) {
        Health health = healthMonitor.health();
        Map<String, Object> details = health.getDetails();  // Getting health details as Map<String, Object>
        Map<String, String> timeUp = healthMonitorService.fetchTimeUp(health);  // Fetching time-up data

        // Create a list to hold website status info (URL, Status, Time Up)
        List<Map<String, String>> websiteStatusData = new ArrayList<>();

        // Loop through all the URLs and check the status and time-up information
        details.forEach((url, status) -> {
            String statusStr = status != null ? status.toString() : "N/A";
            String timeUpStr = timeUp.containsKey(url) ? timeUp.get(url) : "N/A";

            // Create a map for each website's status info
            Map<String, String> websiteStatus = Map.of(
                "url", url,
                "status", statusStr,
                "timeUp", timeUpStr
            );

            websiteStatusData.add(websiteStatus);
        });

        // Pass the list of website status data to the view
        model.addAttribute("websiteStatusData", websiteStatusData);

        return "healthStatuses";
    }
}

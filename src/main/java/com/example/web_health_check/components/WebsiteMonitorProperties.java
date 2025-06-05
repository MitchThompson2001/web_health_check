/*
 * Name: Mitchell Thompson
 * File: HealthMonitor.java
 * Date: 6-5-2025
 * Project: Web Health Check
 * Description:
 */

package com.example.web_health_check.components;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "monitor")
public class WebsiteMonitorProperties {

    private List<String> urls;
    private int interval;

    public List<String> getUrls() {
        return urls;
    }
    public int getInterval() {
        return interval;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }    
}

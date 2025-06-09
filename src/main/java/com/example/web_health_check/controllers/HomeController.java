/*
 * Name: Mitchell Thompson
 * File: HomeController.java
 * Project: Data Viewer Application
 */

package com.example.web_health_check.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Controller class with the primary focus of ensuring only authenticated users
 * can access the home page as well as everything connected to it. This can
 * be implemented better but is functional and simple for now.
 */
@Controller
public class HomeController {

    /*
     * Will redirect user to login route if not signed in. 
     * Otherwise will direct them to the home route 
     */
    @GetMapping("/")
    public String redirectRoot(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() &&
            !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/get_health_statuses";
        }
        return "redirect:/login";
    }

    /*
     * Will redirect user to home route if already logged in. 
     * Otherwise will direct them to the login route
     */
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            // Already logged in — redirect to home
            return "redirect:/get_health_statuses";
        }
        return "login";
    }
}
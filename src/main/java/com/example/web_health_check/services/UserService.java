/*
 * Name: Mitchell Thompson
 * File: UserService.java
 * Project: Data Viewer Application
 */

package com.example.web_health_check.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.web_health_check.models.User;
import com.example.web_health_check.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService implements UserDetailsService {
    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        
        userRepository.save(user);
    }

    public User getByID(long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> 
            new EntityNotFoundException("Entity not found for ID " + id));
    }

    public void deleteByID(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> 
            new EntityNotFoundException("Entity not found for username " + username));
        
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}

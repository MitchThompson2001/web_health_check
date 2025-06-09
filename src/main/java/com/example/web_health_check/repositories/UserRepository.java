/*
 * Name: Mitchell Thompson
 * File: UserRepository.java
 * Project: Data Viewer Application
 */

package com.example.web_health_check.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.web_health_check.models.User; 


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

package com.whyq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.whyq.dto.UserDTO;
import com.whyq.entity.User;
import com.whyq.repository.UserRepository;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //  Register User with Exception Handling
    public void registerUser(UserDTO userDTO) {
        log.info("registerUser() - Attempting to register user with email: {}", userDTO.getEmail());
        try {
            if (userRepository.existsById(userDTO.getEmail())) {
                log.warn("registerUser() - User already exists with email: {}", userDTO.getEmail());
                throw new RuntimeException("User with this email already exists!");
            }

            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encrypt password
            user.setPhoneNumber(userDTO.getPhoneNumber());

            userRepository.save(user);
            log.info("registerUser() - User registered successfully: {}", userDTO.getEmail());

        } catch (Exception e) {
            log.error("registerUser() - Error registering user: {}", e.getMessage());
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }
    }

    //  Authenticate User with Exception Handling
    public User authenticateUser(String email, String password) {
        log.info("authenticateUser() - Attempting to authenticate user: {}", email);
        try {
            User user = userRepository.findByEmail(email);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                log.info("authenticateUser() - User authenticated successfully: {}", email);
                return user; // Login successful
            }
            log.warn("authenticateUser() - Invalid credentials for user: {}", email);
            return null; // Invalid credentials

        } catch (Exception e) {
            log.error("authenticateUser() - Error during authentication: {}", e.getMessage());
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    //  Get User Name by Email with Exception Handling
    public String getUserNameByEmail(String email) {
        log.info("getUserNameByEmail() - Fetching username for email: {}", email);
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                log.warn("getUserNameByEmail() - User not found with email: {}", email);
                throw new RuntimeException("User not found");
            }
            log.info("getUserNameByEmail() - Retrieved username successfully for email: {}", email);
            return user.getName(); // Fetch and return only the name

        } catch (Exception e) {
            log.error("getUserNameByEmail() - Error fetching username: {}", e.getMessage());
            throw new RuntimeException("Error fetching username: " + e.getMessage());
        }
    }
}
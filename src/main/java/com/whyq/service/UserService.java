package com.whyq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.whyq.dto.UserDTO;
import com.whyq.entity.User;
import com.whyq.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getEmail())) {
            throw new RuntimeException("User with this email already exists!");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encrypt password
        user.setPhoneNumber(userDTO.getPhoneNumber());

        userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // Login successful
        }
        return null; // Invalid credentials
    }
}


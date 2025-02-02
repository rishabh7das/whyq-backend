package com.whyq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for testing (Enable in production)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/registerSalonOwner", "/**", "/js/**").permitAll() // Allow unrestricted access
                .requestMatchers("/").authenticated() // Require authentication for dashboard
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable()); // Disabling default Spring Security login page

        return http.build();
    }
}
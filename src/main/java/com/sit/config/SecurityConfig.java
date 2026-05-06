package com.sit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sit.utility.JwtFilter;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // 👈 ADD THIS
public class SecurityConfig {

        @Autowired
        private JwtFilter jwtFilter;
        
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                    .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                    .requestMatchers("/api/users/admin/register").permitAll()

                    .requestMatchers("/api/trips/**").permitAll() // 👈 fix

                    .requestMatchers("/api/users/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")

                    .anyRequest().authenticated()
                );

            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
}
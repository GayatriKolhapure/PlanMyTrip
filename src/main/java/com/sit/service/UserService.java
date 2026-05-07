package com.sit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sit.dto.LoginRequestDto;
import com.sit.dto.RegisterUserRequestDto;
import com.sit.model.User;
import com.sit.repository.UserRepository;
import com.sit.utility.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder encoder;

    public UserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    // 🔷 REGISTER USER (always ROLE_USER)
    public User register(RegisterUserRequestDto request) {

        // 🔴 Check if email already exists
        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setFName(request.getFName());
        user.setLName(request.getLName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        user.setSecurityQuestion(request.getSecurityQuestion());
        user.setSecurityAnswer(encoder.encode(request.getSecurityAnswer()));


        return repo.save(user);
    }

    // 🔷 LOGIN USER (JWT)
    public String login(LoginRequestDto request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    // 🔷 CREATE ADMIN (only once)
    public User createAdmin(RegisterUserRequestDto request) {

        boolean adminExists = repo.existsByRole("ROLE_ADMIN");

        if (adminExists) {
            throw new RuntimeException("Admin already exists");
        }

        User user = new User();

        user.setFName(request.getFName());
        user.setLName(request.getLName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        user.setRole("ROLE_ADMIN");

        // ❌ No budget / interests

        return repo.save(user);
    }
    public String getSecurityQuestion(String email) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getSecurityQuestion().getQuestion();
    }
    public String verifyAnswer(String email, String answer) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(answer, user.getSecurityAnswer())) {
            throw new RuntimeException("Wrong answer");
        }

        return "Answer correct";
    }
    public String resetPassword(String email, String newPassword) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(newPassword));

        repo.save(user);

        return "Password updated";
    }
}
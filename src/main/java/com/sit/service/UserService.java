package com.sit.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sit.dto.LoginRequestDto;
import com.sit.dto.LoginResponseDto;
import com.sit.dto.RegisterUserRequestDto;
import com.sit.dto.UserResponse;
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
    
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getFName(),
            user.getLName(),
            user.getEmail(),
            user.getRole()
        );
    }

    // 🔷 REGISTER USER (always ROLE_USER)
    public UserResponse register(RegisterUserRequestDto request) {

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

        User saved = repo.save(user);

        // ✅ Convert to response DTO
        return mapToResponse(saved);
    }

    // 🔷 LOGIN USER (JWT)
    public LoginResponseDto login(LoginRequestDto request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

//        // ✅ Generate JWT token
//        return jwtUtil.generateToken(user.getEmail(), user.getRole());
        
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new LoginResponseDto(
                user.getId(),
                token
        );
    }
    
    // 🔷 CREATE ADMIN (only once)
    public UserResponse createAdmin(RegisterUserRequestDto request) {

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

        User saved = repo.save(user);

        // ✅ Convert to UserResponse
        return mapToResponse(saved);
    }
    
    private final Set<String> verifiedUsers = new HashSet<>();
    
    public String getSecurityQuestion(String email) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getSecurityQuestion().getQuestion();
    }
    
    
    public boolean verifyAnswer(String email, String answer) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isCorrect = encoder.matches(answer, user.getSecurityAnswer());

        if (!isCorrect) {
            throw new RuntimeException("Wrong answer");
        }

        // ✅ mark user as verified
        verifiedUsers.add(email);

        return true;
    }
   
    
    public String resetPassword(String email, String newPassword) {

        // ❌ block if not verified
        if (!verifiedUsers.contains(email)) {
            throw new RuntimeException("Please verify security question and answer first");
        }

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(newPassword));
        repo.save(user);

        // ✅ remove after reset (important)
        verifiedUsers.remove(email);

        return "Password updated successfully";
    }

		public User getProfile(Long id) {
		
		    return repo.findById(id)
		            .orElseThrow(() ->
		                new RuntimeException("User not found"));
		}
		
		public User updateProfile(Long id, User newData) {
		
		    User old = getProfile(id);
		
		    old.setPhone(newData.getPhone());
		    old.setAddress(newData.getAddress());
		    old.setGender(newData.getGender());
		    old.setBio(newData.getBio());
		    old.setDateOfBirth(newData.getDateOfBirth());
		
		    return repo.save(old);
		}
		}




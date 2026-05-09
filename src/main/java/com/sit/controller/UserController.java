package com.sit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sit.dto.ApiResponse;
import com.sit.dto.ForgotPasswordDto;
import com.sit.dto.LoginRequestDto;
import com.sit.dto.RegisterUserRequestDto;
import com.sit.dto.ResetPasswordDto;
import com.sit.dto.UserResponse;
import com.sit.dto.VerifyAnswerDto;
import com.sit.model.User;
import com.sit.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/register")
	public ApiResponse<UserResponse> register(@RequestBody RegisterUserRequestDto request) {

	    UserResponse user = service.register(request);

	    return new ApiResponse<>(
	            "success",
	            "User registered successfully",
	            user
	    );
	}
	
	@PostMapping("/login")
	public ApiResponse<UserResponse> login(@RequestBody LoginRequestDto request) {

	    UserResponse user = service.login(request); 

	    return new ApiResponse<>(
	            "success",
	            "Login successful",
	            user
	    );
	}

	@PostMapping("/admin/register")
	public ApiResponse<UserResponse> createAdmin(@RequestBody RegisterUserRequestDto request) {

	    UserResponse admin = service.createAdmin(request);

	    return new ApiResponse<>(
	            "success",
	            "Admin created successfully",
	            admin
	    );
	}
	
	@PostMapping("/forgot-password")
	public ApiResponse<String> forgotPassword(@RequestBody ForgotPasswordDto req) {

	    String question = service.getSecurityQuestion(req.getEmail());

	    return new ApiResponse<>(
	            "success",
	            "Security question fetched",
	            question
	    );
	}

	@PostMapping("/verify-answer")
	public ApiResponse<Boolean> verifyAnswer(@RequestBody VerifyAnswerDto request) {

	    boolean result = service.verifyAnswer(request.getEmail(), request.getAnswer());

	    return new ApiResponse<>(
	            "success",
	            "Answer verified successfully",
	            result
	    );
	}
	
	@PutMapping("/reset-password")
	public ApiResponse<Boolean> resetPassword(@RequestBody ResetPasswordDto req) {

	    service.resetPassword(req.getEmail(), req.getNewPassword());

	    return new ApiResponse<>(
	            "success",
	            "Password updated successfully",
	            true
	    );
	}
	
	
	

}

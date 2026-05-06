package com.sit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sit.dto.LoginRequestDto;
import com.sit.dto.RegisterUserRequestDto;
import com.sit.model.User;
import com.sit.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/register")
	public User register(@RequestBody RegisterUserRequestDto request) {
		return service.register(request);
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequestDto request) {
		System.out.println("LOGIN API HIT");
		return service.login(request);
	}

	@PostMapping("/admin/register")
	public User createAdmin(@RequestBody RegisterUserRequestDto request) {
		System.out.println("USER: " + SecurityContextHolder.getContext().getAuthentication().getName());
		return service.createAdmin(request);
	}
	
	//public User 
	
	
	

}

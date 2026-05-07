package com.sit.dto;

import com.sit.enums.SecurityQuestion;

import lombok.Data;

@Data
public class RegisterUserRequestDto {
    private String fName;
    private String lName;
    private String email;
    private String password;
    
    private SecurityQuestion securityQuestion;
    private String securityAnswer;
  
}

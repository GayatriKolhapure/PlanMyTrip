package com.sit.dto;

import lombok.Data;

@Data
public class RegisterUserRequestDto {
    private String fName;
    private String lName;
    private String email;
    private String password;
  
}

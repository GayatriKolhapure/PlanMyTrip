package com.sit.dto;


import lombok.Data;

@Data
public class RegisterAdminRequestDto {

    private String fName;
    private String lName;
    private String email;
    private String password;
}
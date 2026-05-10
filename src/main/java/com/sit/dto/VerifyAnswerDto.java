package com.sit.dto;

import lombok.Data;

@Data
public class VerifyAnswerDto {
    private String email;   // keep this (hidden in frontend)
    private String answer;
}

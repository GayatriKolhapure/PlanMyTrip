package com.sit.model;


import com.sit.enums.SecurityQuestion;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fName")
    private String fName;

    @Column(name = "lName")
    private String lName;

    @Column(unique = true)
    private String email;

    private String password;
    
    private String role;
    
    @Enumerated(EnumType.STRING)
    private SecurityQuestion securityQuestion;
    private String securityAnswer;

}

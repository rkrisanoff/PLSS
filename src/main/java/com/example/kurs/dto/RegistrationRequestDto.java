package com.example.kurs.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String username;
    private String password;
    private String password_confirmation;
    private String first_name;
    private String last_name;
    private String patronymic;
    private Integer age;
    private String gender;
    private String role;
}

package com.example.kurs.dto;

import lombok.Data;

@Data
public class PostDto {
    private String employee_username;
    private String rolename;
    private Long department_id;
    private Integer premium;
}

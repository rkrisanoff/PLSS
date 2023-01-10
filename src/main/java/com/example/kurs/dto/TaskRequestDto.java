package com.example.kurs.dto;

import lombok.Data;

@Data
public class TaskRequestDto {
    private String description;
    private String state;
    private Long creator_id;
    private Long executor_id;
    private Double cost;
}

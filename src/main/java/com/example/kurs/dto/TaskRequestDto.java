package com.example.kurs.dto;

import lombok.Data;

@Data
public class TaskRequestDto {
    private String description;
    private String state;
    private Long creatorPostId;
    private Long executorPostId;
    private Double cost;
}

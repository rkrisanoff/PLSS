package com.example.kurs.dto;

import lombok.Data;

@Data
public class RobotDto {
    private Long brain_series;
    private Long body_series;
    private Long eye_series;
    private Long operator_post_id;
    private Integer hit_points;
    private Long asteroid_id;
}

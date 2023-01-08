package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Robot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long brain_series;
    private Long body_series;
    private Long eye_series;
    private Long operator_post_id;
    private Long asteroid_id;
    private Integer hit_points;

    public Robot() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrain_series() {
        return brain_series;
    }

    public void setBrain_series(Long brain_series) {
        this.brain_series = brain_series;
    }

    public Long getBody_series() {
        return body_series;
    }

    public void setBody_series(Long body_series) {
        this.body_series = body_series;
    }

    public Long getEye_series() {
        return eye_series;
    }

    public void setEye_series(Long eye_series) {
        this.eye_series = eye_series;
    }

    public Long getOperator_post_id() {
        return operator_post_id;
    }

    public void setOperator_post_id(Long operator_post_id) {
        this.operator_post_id = operator_post_id;
    }

    public Long getAsteroid_id() {
        return asteroid_id;
    }

    public void setAsteroid_id(Long asteroid_id) {
        this.asteroid_id = asteroid_id;
    }

    public Integer getHit_points() {
        return hit_points;
    }

    public void setHit_points(Integer hit_points) {
        this.hit_points = hit_points;
    }
}

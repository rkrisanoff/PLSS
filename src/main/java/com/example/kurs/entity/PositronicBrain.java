package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PositronicBrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long release_series;
    private String name;
    private Integer speed;
    private Integer cost;

    public PositronicBrain() {
    }

    public Long getRelease_series() {
        return release_series;
    }

    public void setRelease_series(Long release_series) {
        this.release_series = release_series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}

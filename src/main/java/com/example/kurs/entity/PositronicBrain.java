package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PositronicBrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long releaseSeries;
    private String name;
    private Integer speed;
    private Integer cost;

    public PositronicBrain() {
    }

    public Long getReleaseSeries() {
        return releaseSeries;
    }

    public void setReleaseSeries(Long releaseSeries) {
        this.releaseSeries = releaseSeries;
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

package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EyesSensors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long releaseSeries;
    private String name;
    private Integer distance;
    private Integer cost;

    public EyesSensors() {
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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}

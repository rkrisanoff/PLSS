package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EyesSensors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long release_series;
    private String name;
    private Integer distance;
    private Integer cost;

    public EyesSensors() {
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

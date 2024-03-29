package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long releaseSeries;
    private String name;
    private Double max_hit_points;
    private Integer cost;

    public Body() {
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

    public Double getMax_hit_points() {
        return max_hit_points;
    }

    public void setMax_hit_points(Double max_hit_points) {
        this.max_hit_points = max_hit_points;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}

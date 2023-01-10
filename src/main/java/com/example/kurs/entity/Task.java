package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String state;
    private Long creator_post_id;
    private Long executor_post_id;
    private Double cost;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCreator_post_id() {
        return creator_post_id;
    }

    public void setCreator_post_id(Long creator_post_id) {
        this.creator_post_id = creator_post_id;
    }

    public Long getExecutor_post_id() {
        return executor_post_id;
    }

    public void setExecutor_post_id(Long executor_post_id) {
        this.executor_post_id = executor_post_id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}

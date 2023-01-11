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
    private Long creatorPostId;
    private Long executorPostId;
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

    public Long getCreatorPostId() {
        return creatorPostId;
    }

    public void setCreatorPostId(Long creatorPostId) {
        this.creatorPostId = creatorPostId;
    }

    public Long getExecutorPostId() {
        return executorPostId;
    }

    public void setExecutorPostId(Long executorPostId) {
        this.executorPostId = executorPostId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}

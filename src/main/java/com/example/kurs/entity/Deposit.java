package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long asteroidId;
    private Double bor_quantity;

    public Deposit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAsteroid_id() {
        return asteroidId;
    }

    public void setAsteroid_id(Long asteroid_id) {
        this.asteroidId = asteroid_id;
    }

    public Double getBor_quantity() {
        return bor_quantity;
    }

    public void setBor_quantity(Double bor_quantity) {
        this.bor_quantity = bor_quantity;
    }
}

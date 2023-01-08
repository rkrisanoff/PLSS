package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MicroreactorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer b2_h6_consumption_rate;
    private Integer b5_h12_consumption_rate;
    private Integer b10_h14_consumption_rate;
    private Integer b12_h12_consumption_rate;

    public MicroreactorType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getB2_h6_consumption_rate() {
        return b2_h6_consumption_rate;
    }

    public void setB2_h6_consumption_rate(Integer b2_h6_consumption_rate) {
        this.b2_h6_consumption_rate = b2_h6_consumption_rate;
    }

    public Integer getB5_h12_consumption_rate() {
        return b5_h12_consumption_rate;
    }

    public void setB5_h12_consumption_rate(Integer b5_h12_consumption_rate) {
        this.b5_h12_consumption_rate = b5_h12_consumption_rate;
    }

    public Integer getB10_h14_consumption_rate() {
        return b10_h14_consumption_rate;
    }

    public void setB10_h14_consumption_rate(Integer b10_h14_consumption_rate) {
        this.b10_h14_consumption_rate = b10_h14_consumption_rate;
    }

    public Integer getB12_h12_consumption_rate() {
        return b12_h12_consumption_rate;
    }

    public void setB12_h12_consumption_rate(Integer b12_h12_consumption_rate) {
        this.b12_h12_consumption_rate = b12_h12_consumption_rate;
    }
}

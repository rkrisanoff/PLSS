package com.example.kurs.entity;

import com.example.kurs.dto.SpaceshipRequestDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Spaceship {
    @Id
    @GeneratedValue
    private Long id;
    private Integer b2_h6_quantity;
    private Integer b5_h12_quantity;
    private Integer b10_h14_quantity;
    private Integer b12_h12_quantity;
    private Long department_id;
    private Integer income;

    public Spaceship() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getB2_h6_quantity() {
        return b2_h6_quantity;
    }

    public void setB2_h6_quantity(Integer b2_h6_quantity) {
        this.b2_h6_quantity = b2_h6_quantity;
    }

    public Integer getB5_h12_quantity() {
        return b5_h12_quantity;
    }

    public void setB5_h12_quantity(Integer b5_h12_quantity) {
        this.b5_h12_quantity = b5_h12_quantity;
    }

    public Integer getB10_h14_quantity() {
        return b10_h14_quantity;
    }

    public void setB10_h14_quantity(Integer b10_h14_quantity) {
        this.b10_h14_quantity = b10_h14_quantity;
    }

    public Integer getB12_h12_quantity() {
        return b12_h12_quantity;
    }

    public void setB12_h12_quantity(Integer b12_h12_quantity) {
        this.b12_h12_quantity = b12_h12_quantity;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }
}

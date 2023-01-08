package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer extracted_bor_quantity;
    private Integer current_resource;

    public Department() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExtracted_bor_quantity() {
        return extracted_bor_quantity;
    }

    public void setExtracted_bor_quantity(Integer extracted_bor_quantity) {
        this.extracted_bor_quantity = extracted_bor_quantity;
    }

    public Integer getCurrent_resource() {
        return current_resource;
    }

    public void setCurrent_resource(Integer current_resource) {
        this.current_resource = current_resource;
    }
}

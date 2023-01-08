package com.example.kurs.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private Integer salary;
    private Boolean can_operate_robot;
    @Transient
    private List<Role> roles;

    public Role() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Boolean getCan_operate_robot() {
        return can_operate_robot;
    }

    public void setCan_operate_robot(Boolean can_operate_robot) {
        this.can_operate_robot = can_operate_robot;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

package com.example.kurs.entity;


import lombok.Getter;

import javax.persistence.*;

public enum Role {
    USER ("user"),
     MODERATOR ("moderator");
    private final String name;

    Role(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

};

package com.example.kurs.entity;

public enum Status {
    MODERATION ("moderation"),

    PUBLISHED ("published"),
    REJECTED ("rejected");
    private final String name;
    Status(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

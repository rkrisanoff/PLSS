package com.example.kurs.entity;

public enum Kitchen {
    RUSSIAN ("russian"),

    CHINESE ("CHINESE"),
    ITALIAN ("ITALIAN"),
    JAPAN ("japan"),
    NONE("none");
    private final String name;
    Kitchen(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

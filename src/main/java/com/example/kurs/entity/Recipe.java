package com.example.kurs.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Column(name = "author_id")
    @Getter
    @Setter
    private Long authorId;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter

    private String description;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Status status;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private Kitchen kitchen;

    public Recipe() {
    }
}

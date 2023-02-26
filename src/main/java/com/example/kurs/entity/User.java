package com.example.kurs.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Getter
    @Setter
    private Role role;

    public User() {
    }
}

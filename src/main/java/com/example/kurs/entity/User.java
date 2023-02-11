package com.example.kurs.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    @Getter
    private Role role;

    public User() {
    }
}

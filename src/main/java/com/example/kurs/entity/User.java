package com.example.kurs.entity;


import lombok.Data;


import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Column(name="favorite_recipe_id")
    private Long favoriteRecipeId;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public User() {
    }
}

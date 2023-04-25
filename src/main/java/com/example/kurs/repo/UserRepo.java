package com.example.kurs.repo;

import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);


    @Modifying
    @Query("update User user set user.favoriteRecipeId = ?1")
    int setFavoriteRecipeForUser(Long favoriteRecipeId);
}

package com.example.kurs.repo;

import com.example.kurs.entity.Recipe;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;


public interface RecipeRepo extends CrudRepository<Recipe, Long> {
    Recipe save(Recipe recipe);
    List<Recipe> findByStatus (String status);
    List<Recipe> findAll();
    Recipe getById(Long Id);
    @Modifying
    @Query("update Recipe recipe set recipe.status = ?1 where recipe.id = ?2")
    int setStatusForRecipe(String status, Long id);
}

package com.example.kurs.repo;

import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepo extends CrudRepository<Recipe, Long> {
}

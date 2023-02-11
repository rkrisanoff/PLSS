package com.example.kurs.service;

import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.repo.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;


    public Recipe getById(Long id){
        Optional<Recipe> recipe = recipeRepo.findById(id);
        if (!recipe.isPresent()){
            log.info("Recipe with id {} not found.", id);
            return null;
        }
        log.info("Found recipe with id {}.", id);
        return recipe.get();

    }


    public List<Recipe> getAll(){
        return (List<Recipe>) recipeRepo.findAll();
    }

    public void changeStatus(Long id, Status status){
        Optional<Recipe> recipe = recipeRepo.findById(id);
        if (!recipe.isPresent()){
            log.info("Recipe with id {} not found.", id);
            return;
        }
        log.info("Found recipe with id {}.", id);
        recipe.get().setStatus(status);
        recipeRepo.save(recipe.get());
    }
}

package com.example.kurs.service;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.entity.Kitchen;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.exceptions.IllegalKitchenException;
import com.example.kurs.repo.RecipeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;

    public List<Recipe> getRecipesListOnModeration(int page, int size, String sortDir, String sort) {
        PageRequest pageReq
                = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        Page<Recipe> recipes = recipeRepo.findAllByStatus(Status.MODERATION, pageReq);
        return recipes.getContent();
    }

    public List<Recipe> getApprovedRecipesList(int page, int size, String sortDir, String sort) {
        PageRequest pageReq
                = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        Page<Recipe> recipes = recipeRepo.findAllByStatus(Status.APPROVED, pageReq);
        return recipes.getContent();
    }


//    public Recipe getById(Long id) {
//        Optional<Recipe> recipe = recipeRepo.findById(id);
//        if (!recipe.isPresent()) {
//            log.info("Recipe with id {} not found.", id);
//            return null;
//        }
//        log.info("Found recipe with id {}.", id);
//        return recipe.get();
//
//    }

    private Recipe recipeDtoToRecipe(RecipeDto recipeDto) throws IllegalKitchenException {
        Recipe recipe = null;
        recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        try {
            recipe.setKitchen(Kitchen.valueOf(recipeDto.getKitchen().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalKitchenException("Illegal kitchen");
        }
        return recipe;
    }

    public void addRecipe(RecipeDto recipeDto, Long id) throws IllegalKitchenException{
        Recipe recipe = recipeDtoToRecipe(recipeDto);
        recipe.setAuthorId(id);
        recipe.setStatus(Status.MODERATION);
        recipeRepo.save(recipe);
    }


    public Optional<Recipe> getRecipeOnModerationId(Long id) {
        return recipeRepo.findById(id);
    }

    @Transactional

    public Integer changeStatus(Long id, Status status) {
        return recipeRepo.setStatusForRecipe(status, id);
    }
}

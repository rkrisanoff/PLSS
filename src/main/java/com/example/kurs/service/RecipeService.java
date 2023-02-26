package com.example.kurs.service;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.entity.Kitchen;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
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


    public Recipe getById(Long id) {
        Optional<Recipe> recipe = recipeRepo.findById(id);
        if (!recipe.isPresent()) {
            log.info("Recipe with id {} not found.", id);
            return null;
        }
        log.info("Found recipe with id {}.", id);
        return recipe.get();

    }

    private Boolean checkTitle(String title) {
        return title != null && title.length() > 0 && title.length() <= 32;
    }

    private Boolean checkDescription(String description) {
        return description != null && description.length() > 0 && description.length() <= 4096;
    }

    private Boolean checkKitchen(String kitchen) {
        try {
            Kitchen.valueOf(kitchen.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }


    private Boolean checkValidRecipeDto(RecipeDto recipeDto) {
        String title = recipeDto.getTitle();
        String description = recipeDto.getDescription();
        String kitchen = recipeDto.getKitchen();
        return checkTitle(title) && checkDescription(description) && checkKitchen(kitchen);
    }

    private Recipe recipeDtoToRecipe(RecipeDto recipeDto) {
        Recipe recipe = null;
        if (checkValidRecipeDto(recipeDto)) {
            recipe = new Recipe();
            recipe.setTitle(recipeDto.getTitle());
            recipe.setDescription(recipeDto.getDescription());
            recipe.setKitchen(Kitchen.valueOf(recipeDto.getKitchen().toUpperCase()));
        }
        return recipe;
    }

    public Boolean pushNewResive(RecipeDto recipeDto, Long id) {
        Recipe recipe = recipeDtoToRecipe(recipeDto);
        if (recipe != null) {
            recipe.setAuthorId(id);
            recipe.setStatus(Status.MODERATION);
            recipeRepo.save(recipe);
            return true;
        }
        return false;
    }


    public Optional<Recipe> getRecipeOnModerationId(Long id) {
        return recipeRepo.findById(id);
    }

    @Transactional

    public Integer changeStatus(Long id, Status status) {
        return recipeRepo.setStatusForRecipe(status, id);
    }
}

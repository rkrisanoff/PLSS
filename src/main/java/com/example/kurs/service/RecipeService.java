package com.example.kurs.service;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.entity.Kitchen;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.exceptions.IllegalKitchenException;
import com.example.kurs.exceptions.InvalidPageNumberException;
import com.example.kurs.exceptions.InvalidSizeException;
import com.example.kurs.exceptions.InvalidSortDirectionException;
import com.example.kurs.repo.RecipeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;
    private Set<String> sortDirs = Set.of("ASC", "DESC");
    @Resource
    private UserTransaction userTransaction;

    private void validatePaginationParameters(int page, int size, String sortDir) throws InvalidPageNumberException, InvalidSizeException, InvalidSortDirectionException {
        if (page < 0) {
            throw new InvalidPageNumberException("The specified " + page + " is invalid. Page must be greater than zero");
        }
        if (size <= 0) {
            throw new InvalidSizeException("The specified size " + size + " is invalid");
        }
        if (!sortDirs.contains(sortDir)) {
            throw new InvalidSortDirectionException("The specified sorting direction" + sortDir + " is invalid");
        }
    }

    public List<Recipe> getRecipesListOnModeration(int page, int size, String sortDir, String sort) throws InvalidSizeException, InvalidSortDirectionException, InvalidPageNumberException {
        validatePaginationParameters(page, size, sortDir);
        PageRequest pageReq
                = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        Page<Recipe> recipes = recipeRepo.findAllByStatus(Status.MODERATION, pageReq);
        return recipes.getContent();
    }

    public List<Recipe> getApprovedRecipesList(int page, int size, String sortDir, String sort) throws InvalidPageNumberException,
            InvalidSizeException, InvalidSortDirectionException {
        validatePaginationParameters(page, size, sortDir);
        PageRequest pageReq
                = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        Page<Recipe> recipes = recipeRepo.findAllByStatus(Status.APPROVED, pageReq);
        return recipes.getContent();
    }


    private Recipe recipeDtoToRecipe(RecipeDto recipeDto) throws IllegalKitchenException {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        try {
            recipe.setKitchen(Kitchen.valueOf(recipeDto.getKitchen().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalKitchenException("Illegal kitchen");
        }
        return recipe;
    }

    public void addRecipe(RecipeDto recipeDto, Long id) throws IllegalKitchenException, SystemException {
        try {
            userTransaction.begin();
            Recipe recipe = recipeDtoToRecipe(recipeDto);
            recipe.setAuthorId(id);
            recipe.setStatus(Status.MODERATION);
            recipeRepo.save(recipe);
            userTransaction.commit();
        } catch (Exception e) {
            if (userTransaction != null) {
                userTransaction.rollback();
            }

        }
    }


    public Optional<Recipe> getRecipeOnModerationId(Long id) {
        return recipeRepo.findById(id);
    }

    public Integer changeStatus(Long id, Status status) throws SystemException {
        Integer changedLines = 0;
        try {
            userTransaction.begin();
            changedLines = recipeRepo.setStatusForRecipe(status, id);
            userTransaction.commit();
        } catch (Exception e) {
            if (userTransaction != null) {
                userTransaction.rollback();
            }
        }
        return changedLines;
    }
}

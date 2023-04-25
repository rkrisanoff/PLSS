package com.example.kurs.controller;

import com.example.kurs.dto.FavoriteRecipeRequestDto;
import com.example.kurs.dto.RecipeDto;
import com.example.kurs.entity.Recipe;
import com.example.kurs.exceptions.*;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.SystemException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;

    @PostMapping("/add-recipe")
    public ResponseEntity<String> addRecipe(
            @Valid @RequestBody RecipeDto requestRecipe,
            Authentication authentication
    ) throws IllegalKitchenException, UserAlreadyExistsException, SystemException {

        if (!userService.existsByUsername(authentication.getName())) {
            throw new UserAlreadyExistsException("User with username = " + authentication.getName() + " doesn't exits");
        }
        recipeService.addRecipe(requestRecipe, userService.getByUsername(authentication.getName()).getId());
        return ResponseEntity.ok("");
    }

    @GetMapping("/recipes/all")
    public List<Recipe> getRecipes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir,
            @RequestParam(value = "sort", defaultValue = "id") String sort) throws InvalidSizeException, InvalidSortDirectionException, InvalidPageNumberException {


        List<Recipe> recipes = recipeService.getApprovedRecipesList(page, size, sortDir, sort);

        return new ArrayList<>(recipes);
    }

    @PostMapping("recipes/favorite")
    public ResponseEntity<String> setFavoriteRecipe(
            @Valid @RequestBody FavoriteRecipeRequestDto favoriteRecipeDto,
            Authentication authentication
    ) throws SystemException, UserNotFoundException, RecipeNotFoundException {
        userService.setFavoriteRecipe(authentication.getName(), favoriteRecipeDto.getRecipeId());
        return ResponseEntity.ok("");
    }

    @GetMapping("recipes/favorite")
    public ResponseEntity<Recipe> getFavoriteRecipe(Authentication authentication) throws UserNotFoundException, RecipeNotFoundException {
        Optional<Recipe> recipe = userService.getFavoriteRecipe(authentication.getName());
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(null));
    }
    @DeleteMapping("recipes/favorite")
    public ResponseEntity<String> deleteFavoriteRecipe(Authentication authentication) throws SystemException, UserNotFoundException {
        userService.deleteFavoriteRecipe(authentication.getName());
        return ResponseEntity.ok("");
    }
}

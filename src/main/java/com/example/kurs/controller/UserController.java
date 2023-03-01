package com.example.kurs.controller;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.entity.Recipe;
import com.example.kurs.exceptions.IllegalKitchenException;
import com.example.kurs.exceptions.JwtAuthenticationException;
import com.example.kurs.exceptions.UserAlreadyExistsException;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;

    @PostMapping("/add-recipe")
    public ResponseEntity<String> addRecipe(@Valid @RequestBody RecipeDto requestRecipe, @RequestHeader HttpHeaders header) throws IllegalKitchenException,UserAlreadyExistsException {
        String jwt = header.getFirst("Authorization");
        if(jwt==null || jwt.length()<8){
            throw new JwtAuthenticationException("Your jwt incorrect");
        }
        jwt=jwt.substring(7);
        Long userId = jwtTokenProvider.getId(jwt);
        if (!userService.existsById(userId)) {
            throw new UserAlreadyExistsException("User with id = " + userId + " doesn't exits");
        }
        recipeService.addRecipe(requestRecipe, userId);
        return ResponseEntity.ok("");
    }

    @GetMapping("/recipes/all")
    @ResponseBody
    public List<Recipe> getRecipesOnModeration(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir,
            @RequestParam(value = "sort", defaultValue = "id") String sort) {

        List<Recipe> recipes = recipeService.getApprovedRecipesList(page+1, size, sortDir, sort);

        return new ArrayList<>(recipes);
    }
}

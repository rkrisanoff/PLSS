package com.example.kurs.controller;

import com.example.kurs.entity.Status;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;

    @GetMapping("/recipe/all")
    public ResponseEntity<String> allRecipe() {
        String bodyResponse=recipeService.getAllRecipeOnModeration();
        if(bodyResponse.equals(null)){
            return new ResponseEntity<>("failed to generate response", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bodyResponse, HttpStatus.OK);
    }
    @GetMapping("/recipe/{id}")
    public ResponseEntity<String> recipeId(@PathVariable("id") Long id) {
        String bodyResponse=recipeService.getRecipeOnModerationId(id);
        if(bodyResponse.equals(null)){
            return new ResponseEntity<>("failed to generate response", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bodyResponse, HttpStatus.OK);
    }
    @PatchMapping("/recipe/{id}")
    public ResponseEntity<String> getRecipeId(@RequestParam("status") String status, @PathVariable("id") Long id) {
        Integer countUpdate = recipeService.changeStatus(id, status);
        if(countUpdate==1){
            return new ResponseEntity<>(countUpdate.toString(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("error in setting the status", HttpStatus.NOT_FOUND);
        }
    }

}

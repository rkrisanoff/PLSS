package com.example.kurs.controller;

import com.example.kurs.dto.StatusDTO;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("/recipe/all")
    public ResponseEntity<String> allRecipe() {
        String bodyResponse=recipeService.getAllRecipeOnModeration();
        if(bodyResponse==null){
            return new ResponseEntity<>("failed to generate response", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bodyResponse, HttpStatus.OK);
    }
    @GetMapping("/recipe/{id}")
    public ResponseEntity<String> recipeId(@PathVariable("id") Long id) throws JsonProcessingException {
        Optional<Recipe> recipe =recipeService.getRecipeOnModerationId(id);
        if(!recipe.isPresent()){
            return new ResponseEntity<>("recipe doesn't exist", HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(objectMapper.writeValueAsString(recipe.get()), HttpStatus.OK);
        } catch (JsonProcessingException e){
            return ResponseEntity.status(422).body("trouble with getting the recipe");
        }
    }
    @PatchMapping("/recipe/{id}")
    public ResponseEntity<String> getRecipeId(@RequestBody StatusDTO statusDTO, @PathVariable("id") Long id) {

        try {
            Integer countUpdate = recipeService.changeStatus(id, Status.valueOf(statusDTO.getStatus()));
            if(countUpdate==1){
                return new ResponseEntity<>(countUpdate.toString(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>("error in setting the status", HttpStatus.NOT_FOUND);
            }
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>("error parsing body request", HttpStatus.BAD_REQUEST);
        }
    }

}

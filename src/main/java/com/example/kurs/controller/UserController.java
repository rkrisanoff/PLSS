package com.example.kurs.controller;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<String> createUser(@Valid @RequestBody RecipeDto requestRecipe, @RequestHeader HttpHeaders header) {
        String jwt=header.getFirst("Authorization");
        Long userId= jwtTokenProvider.getId(jwt);
        if(userService.existsById(userId)){
            Boolean pushed = recipeService.pushNewResive(requestRecipe,userId);
            if (!pushed){
                    return ResponseEntity.status(423).body("Failed to create recipe");
            }
        } else {
            return ResponseEntity.status(422).body("User doesn't exist");
        }


        return ResponseEntity.status(200).body("");
}
}

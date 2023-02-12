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
    public ResponseEntity<String> createUser(@RequestBody RecipeDto requestRecipe, @RequestHeader HttpHeaders header) {
        String jwt=header.getFirst("Authorization");
        String token = jwt.substring(7);
        Long userId= jwtTokenProvider.getId(token);
        if(userService.existsById(userId)){
            recipeService.pushNewResive(requestRecipe,userId);
        }else{

            return new ResponseEntity<>("json does not match json schema", HttpStatus.valueOf(420));
        }


        return new ResponseEntity<>("Success", HttpStatus.OK);
}
}

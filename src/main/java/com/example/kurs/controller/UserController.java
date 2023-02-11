package com.example.kurs.controller;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add-recipe")
    public ResponseEntity<String> createUser(@RequestBody RecipeDto requestRecipe, @RequestHeader HttpHeaders header) {
       String jwt=header.getFirst("Authorization");



        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}

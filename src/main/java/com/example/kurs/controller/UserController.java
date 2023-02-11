package com.example.kurs.controller;

import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @PostMapping("/add-recipe")
    public ResponseEntity<String> createUser(@RequestBody Map<String, String> requestRecipe) {
        String title = requestRecipe.get("title");
        String description = requestRecipe.get("description");
        System.out.println(title);

        System.out.println(requestRecipe.keySet());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}

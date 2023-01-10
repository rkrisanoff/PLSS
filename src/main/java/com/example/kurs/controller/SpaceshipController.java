package com.example.kurs.controller;

import com.example.kurs.dto.SpaceshipRequestDto;
import com.example.kurs.entity.Spaceship;
import com.example.kurs.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {
    @Autowired
    private SpaceshipService spaceshipService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody SpaceshipRequestDto spaceshipRequestDto){
        /*Spaceship spaceship = spaceshipService.create(spaceshipRequestDto);
        if (spaceship != null){
            return ResponseEntity.ok("Created.");
        }*/
        return ResponseEntity.ok("ok");
    }
}

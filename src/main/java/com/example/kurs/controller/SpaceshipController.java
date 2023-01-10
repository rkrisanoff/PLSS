package com.example.kurs.controller;

import com.example.kurs.dto.SpaceshipRequestDto;
import com.example.kurs.dto.SpaceshipUpdateRequestDto;
import com.example.kurs.entity.Spaceship;
import com.example.kurs.service.SpaceshipService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {
    @Autowired
    private SpaceshipService spaceshipService;
    @Autowired
    private JsonProvider jsonProvider;
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody SpaceshipRequestDto spaceshipRequestDto){
        Spaceship spaceship = spaceshipService.create(spaceshipRequestDto);
        if (spaceship != null){
            return ResponseEntity.ok("Created.");
        }
        return ResponseEntity.badRequest().body("Department id not specified.");
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody SpaceshipUpdateRequestDto spaceshipUpdateRequestDto){
        Spaceship spaceship = spaceshipService.update(spaceshipUpdateRequestDto);
        if (spaceship != null){
            return ResponseEntity.ok("Updated.");
        }
        return ResponseEntity.badRequest().body("Invalid department.");
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable Long id){
        spaceshipService.deleteById(id);
        return ResponseEntity.ok("Deleted.");
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<Spaceship> spaceshipList = spaceshipService.listall();
        return ResponseEntity.ok(jsonProvider.convertToJson(spaceshipList));
    }

}

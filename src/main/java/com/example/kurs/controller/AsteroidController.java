package com.example.kurs.controller;

import com.example.kurs.dto.ExploreRequestDto;
import com.example.kurs.entity.Asteroid;
import com.example.kurs.entity.Deposit;
import com.example.kurs.entity.Employee;
import com.example.kurs.repo.AsteroidRepo;
import com.example.kurs.repo.RobotRepo;
import com.example.kurs.service.AsteroidService;
import com.example.kurs.service.DepositService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/asteroids")
public class AsteroidController {
    @Autowired
    AsteroidService asteroidService;
    @Autowired
    private RobotRepo robotRepo;
    @Autowired
    private JsonProvider jsonProvider;
    @Autowired
    private DepositService depositService;

    @PostMapping("/explore")
    public ResponseEntity explore(@RequestBody ExploreRequestDto exploreRequestDto) throws JsonProcessingException {
        Long robot_id = exploreRequestDto.getRobot_id();
        if (robot_id == null){
            return ResponseEntity.badRequest().body("Attempt to explore asteroids without specifying robot id.");
        }
        if (robotRepo.findById(robot_id).orElse(null) == null){
            return ResponseEntity.badRequest().body("Robot " + robot_id + " not found.");
        }
        List<Asteroid> asteroids = asteroidService.explore(robot_id);
        return ResponseEntity.ok(jsonProvider.convertToJson(asteroids));
    }

    @GetMapping("/all")
    public ResponseEntity listall() throws JsonProcessingException {
        List<Asteroid> asteroids = asteroidService.getAll();
        return ResponseEntity.ok(jsonProvider.convertToJson(asteroids));
    }

    @GetMapping("/{asteroid_id}/deposits")
    public ResponseEntity listDeposits(@PathVariable Long asteroid_id) throws JsonProcessingException {
        List<Deposit> deposits = depositService.getDepositsOfAsteroid(asteroid_id);
        return ResponseEntity.ok(jsonProvider.convertToJson(deposits));
    }
}

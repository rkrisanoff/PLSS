package com.example.kurs.service;

import com.example.kurs.entity.Asteroid;
import com.example.kurs.entity.Deposit;
import com.example.kurs.entity.EyesSensors;
import com.example.kurs.entity.Robot;
import com.example.kurs.repo.*;
import com.example.kurs.service.generators.AsteroidGenerator;
import com.example.kurs.service.generators.DepositGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class AsteroidService {
    @Autowired
    private DepositRepo depositRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    AsteroidRepo asteroidRepo;
    @Autowired
    RobotRepo robotRepo;
    @Autowired
    EyesRepo eyesRepo;
    @Autowired
    DepositGenerator depositGenerator;
    @Autowired
    AsteroidGenerator asteroidGenerator;
    public List<Asteroid> getAll(){
        List<Asteroid> asteroids = asteroidRepo.findAll();
        log.info("Listed asteroids");
        return asteroids;
    }

    public List<Asteroid> explore(Long robot_id){
        if (robot_id == null || robotRepo.findById(robot_id).orElse(null) == null){
            log.info("No robot {} found.", robot_id);
            return null;
        }
        Robot robot = robotRepo.findById(robot_id).get();
        Long eyes_id = robot.getEye_series();
        if (eyes_id == null || eyesRepo.findById(eyes_id).orElse(null) == null){
            log.info("No eyes of robot {} found.", robot_id);
            return null;
        }
        List<Asteroid> generatedAsteroids = new ArrayList<>();
        EyesSensors eyes = eyesRepo.findById(eyes_id).get();
        int distance = eyes.getDistance();
        int min_asteroids = 1;
        int max_asteroids = (int) Math.floor(5 + distance*0.0001);
        int min_deposits = 1;
        int max_deposits = 10;
        int asteroids_num = ThreadLocalRandom.current().nextInt(min_asteroids, max_asteroids);
        for (int i = 0; i < asteroids_num; i++){
            Asteroid asteroid = asteroidGenerator.generate();
            Asteroid savedAsteroid = asteroidRepo.save(asteroid);
            int deposits_num = ThreadLocalRandom.current().nextInt(min_deposits, max_deposits);
            for (int j = 0; j < deposits_num; j++){
                Deposit randomDeposit = depositGenerator.generate(savedAsteroid.getId());
                depositRepo.save(randomDeposit);
            }
            generatedAsteroids.add(savedAsteroid);
        }
        return generatedAsteroids;
    }
}

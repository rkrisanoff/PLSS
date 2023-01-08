package com.example.kurs.service;

import com.example.kurs.entity.Asteroid;
import com.example.kurs.repo.AsteroidRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AsteroidService {
    @Autowired
    AsteroidRepo asteroidRepo;
    public List<Asteroid> getAll(){
        List<Asteroid> asteroids = asteroidRepo.findAll();
        log.info("Listed asteroids");
        return asteroids;
    }
}

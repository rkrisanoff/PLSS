package com.example.kurs.repo;

import com.example.kurs.entity.Asteroid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsteroidRepo extends JpaRepository<Asteroid, Long> {
}

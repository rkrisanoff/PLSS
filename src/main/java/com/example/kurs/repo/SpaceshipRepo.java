package com.example.kurs.repo;

import com.example.kurs.entity.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceshipRepo extends JpaRepository<Spaceship, Long> {
}

package com.example.kurs.repo;

import com.example.kurs.entity.Robot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobotRepo extends JpaRepository<Robot, Long> {
}

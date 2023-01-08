package com.example.kurs.repo;

import com.example.kurs.entity.EyesSensors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EyesRepo extends JpaRepository<EyesSensors, Long> {
}

package com.example.kurs.repo;

import com.example.kurs.entity.Body;
import com.example.kurs.entity.PositronicBrain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyRepo extends JpaRepository<Body, Long> {
    Body findByReleaseSeries(Long release_series);
}

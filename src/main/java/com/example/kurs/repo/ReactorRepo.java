package com.example.kurs.repo;

import com.example.kurs.entity.MicroreactorInSpaceship;
import com.example.kurs.entity.MicroreactorType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactorRepo extends JpaRepository<MicroreactorType, Long> {
}

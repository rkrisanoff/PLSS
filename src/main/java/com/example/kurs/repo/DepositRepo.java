package com.example.kurs.repo;

import com.example.kurs.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepo extends JpaRepository<Deposit, Long> {
    List<Deposit> findDepositsByAsteroidId(Long asteroid_id);
}

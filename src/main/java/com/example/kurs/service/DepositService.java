package com.example.kurs.service;

import com.example.kurs.entity.Deposit;
import com.example.kurs.repo.DepositRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepositService {
    @Autowired
    public DepositRepo depositRepo;

    public List<Deposit> getDepositsOfAsteroid(Long asteroid_id){
        if (asteroid_id == null){
            log.info("Asteroid id is null.");
            return null;
        }
        List<Deposit> deposits = depositRepo.findDepositsByAsteroidId(asteroid_id);
        return deposits;
    }
}

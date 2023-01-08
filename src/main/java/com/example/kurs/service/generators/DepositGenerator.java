package com.example.kurs.service.generators;

import com.example.kurs.entity.Deposit;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class DepositGenerator {
    private final Integer MIN_BOR = 1;
    private final Integer MAX_BOR = 1000;
    public Deposit generate(Long asteroid_id){
        if (asteroid_id == null){
            return null;
        }
        Double bor_quantity = ThreadLocalRandom.current().nextDouble(MIN_BOR, MAX_BOR);
        Deposit deposit = new Deposit();
        deposit.setBor_quantity(bor_quantity);
        deposit.setAsteroid_id(asteroid_id);
        return deposit;
    }
}

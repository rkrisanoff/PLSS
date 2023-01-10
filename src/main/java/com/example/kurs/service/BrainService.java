package com.example.kurs.service;

import com.example.kurs.entity.PositronicBrain;
import com.example.kurs.repo.PositronicBrainRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BrainService {
    @Autowired
    private PositronicBrainRepo brainRepo;

    public List<PositronicBrain> findAll() {
        List<PositronicBrain> positronicBrains = brainRepo.findAll();
        log.info("Listed brains.");
        return positronicBrains;
    }
}

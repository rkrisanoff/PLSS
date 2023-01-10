package com.example.kurs.service;

import com.example.kurs.repo.EyesRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EyesService {
    @Autowired
    private EyesRepo eyesRepo;
}

package com.example.kurs.service;

import com.example.kurs.entity.Body;
import com.example.kurs.repo.BodyRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BodyService {
    @Autowired
    private BodyRepo bodyRepo;
    public Body findById(Long id){
        if (id == null){
            return null;
        }
        Body body = bodyRepo.findById(id).orElse(null);
        return body;
    }
}

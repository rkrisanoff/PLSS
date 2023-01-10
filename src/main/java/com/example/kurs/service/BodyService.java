package com.example.kurs.service;

import com.example.kurs.entity.Body;
import com.example.kurs.repo.BodyRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BodyService {
    @Autowired
    private BodyRepo bodyRepo;
    public Body findByReleaseSeries(Long release_series){
        if (release_series == null){
            return null;
        }
        Body body = bodyRepo.findByReleaseSeries(release_series);
        return body;
    }

}

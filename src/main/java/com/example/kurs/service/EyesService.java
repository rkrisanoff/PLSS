package com.example.kurs.service;

import com.example.kurs.entity.EyesSensors;
import com.example.kurs.repo.EyesRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EyesService {
    @Autowired
    private EyesRepo eyesRepo;

    public List<EyesSensors> listall(){
        List<EyesSensors> eyesSensors = eyesRepo.findAll();
        log.info("Listed eyes sensors.");
        return eyesSensors;
    }

    public EyesSensors findByReleaseSeries(Long releaseSeries){
        EyesSensors eyes = eyesRepo.findByReleaseSeries(releaseSeries);
        if (eyes != null){
            log.info("Eyes sensors {} found.", releaseSeries);
        } else {
            log.info("Eyes sensors {} not found.", releaseSeries);
        }
        return eyes;
    }
}

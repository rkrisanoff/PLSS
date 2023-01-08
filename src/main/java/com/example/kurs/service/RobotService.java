package com.example.kurs.service;

import com.example.kurs.entity.Robot;
import com.example.kurs.repo.RobotRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RobotService {
    @Autowired
    private RobotRepo robotRepo;
    public void deleteById(Long id){
        robotRepo.deleteById(id);
        log.info("Deleted robot {}.", id);
    }

    public List<Robot> getAll(){
        List<Robot> robots = robotRepo.findAll();
        log.info("Listed robots.");
        return robots;
    }

    public Robot findById(Long id){
        Robot robot = robotRepo.findById(id).orElse(null);
        if (robot == null){
            log.info("Robot {} not found.", id);
        } else {
            log.info("Found robot {}.", id);
        }
        return robot;
    }

    public Robot create(Robot robot){
        if (robotRepo.findById(robot.getId()).orElse(null) != null){
            return null;
        }
        robotRepo.save(robot);
        return robot;
    }

    public Robot update(Robot robot){
        if (robotRepo.findById(robot.getId()).orElse(null) == null){
            return null;
        }
        robotRepo.save(robot);
        return robot;
    }

}

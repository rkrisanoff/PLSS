package com.example.kurs.service;

import com.example.kurs.entity.*;
import com.example.kurs.repo.*;
import com.example.kurs.service.extraction.ExtractionStatus;
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
    @Autowired
    private DepositRepo depositRepo;
    @Autowired
    private AsteroidRepo asteroidRepo;
    @Autowired
    private PositronicBrainRepo positronicBrainRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
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
            log.info("Robot {} already exists.", robot.getId());
            return null;
        }
        robotRepo.save(robot);
        log.info("Robot created.");
        return robot;
    }

    public Robot update(Robot robot){
        if (robotRepo.findById(robot.getId()).orElse(null) == null){
            log.info("Robot {} not found.", robot.getId());
            return null;
        }
        robotRepo.save(robot);
        log.info("Robot {} updated.", robot.getId());
        return robot;
    }

    public ExtractionStatus extract(Robot robot, Long deposit_id, Long department_id){
        Long asteroid_id = robot.getAsteroid_id();
        if (asteroid_id == null){
            log.info("Asteroid id is not specified.");
            return ExtractionStatus.NOT_ON_TARGET_ASTEROID;
        }
        if (department_id == null){
            log.info("Department is not specified");
            return ExtractionStatus.NO_DEPARTMENT;
        }
        Department department = departmentRepo.findById(department_id).orElse(null);
        if (department == null){
            log.info("Department {} not found.", department_id);
            return ExtractionStatus.NO_DEPARTMENT;
        }
        Deposit deposit = depositRepo.findById(deposit_id).orElse(null);
        if (deposit == null){
            log.info("Deposit {} not found.", deposit_id);
            return ExtractionStatus.NO_DEPOSIT;
        }
        if (deposit.getAsteroid_id() != asteroid_id){
            return ExtractionStatus.NOT_ON_TARGET_ASTEROID;
        }
        Long brain_series = robot.getBrain_series();
        PositronicBrain brain = positronicBrainRepo.findById(brain_series).orElse(null);
        if (brain == null){
            return ExtractionStatus.NO_BRAIN;
        }
        double current_boron = deposit.getBor_quantity();
        if (current_boron <= 0){
            return ExtractionStatus.NO_BORON;
        }
        double extraction = brain.getSpeed() * 0.1;
        if (extraction > current_boron){
            extraction = current_boron;
        }
        double after_extraction = current_boron - extraction;
        deposit.setBor_quantity(after_extraction);
        depositRepo.save(deposit);
        department.setExtracted_bor_quantity(department.getExtracted_bor_quantity() + extraction);
        departmentRepo.save(department);

        double damage = extraction * 0.5;
        double after_damage = robot.getHit_points() - damage;
        if (after_damage <= 0){
            robotRepo.deleteById(robot.getId());
            return ExtractionStatus.ROBOT_DEAD;
        }
        robot.setHit_points(after_damage);
        robotRepo.save(robot);
        return ExtractionStatus.OK;
    }

}

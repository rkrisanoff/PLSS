package com.example.kurs.service;

import com.example.kurs.dto.SpaceshipRequestDto;
import com.example.kurs.dto.SpaceshipUpdateRequestDto;
import com.example.kurs.entity.Department;
import com.example.kurs.entity.Spaceship;
import com.example.kurs.repo.SpaceshipRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpaceshipService {
    @Autowired
    private SpaceshipRepo spaceshipRepo;
    @Autowired
    private DepartmentService departmentService;
    public Spaceship create(SpaceshipRequestDto spaceshipRequestDto) {
        Spaceship spaceship = new Spaceship();
        spaceship.setB2_h6_quantity(0);
        spaceship.setB5_h12_quantity(0);
        spaceship.setB10_h14_quantity(0);
        spaceship.setB12_h12_quantity(0);
        spaceship.setIncome(0);
        if (spaceshipRequestDto.getDepartment_id() == null){
            log.info("Department not specified.");
            return null;
        }
        spaceship.setDepartment_id(spaceshipRequestDto.getDepartment_id());
        log.info("Created spaceship.");
        return spaceshipRepo.save(spaceship);
    }

    public void deleteById(Long id) {
        if (id == null){
            log.info("Id of spaceship is null.");
        }
        log.info("Deleted spaceship.");
        spaceshipRepo.deleteById(id);
    }

    public Spaceship update(SpaceshipUpdateRequestDto requestDto){
        Spaceship spaceship = new Spaceship();
        spaceship.setB2_h6_quantity(requestDto.getB2_h6_quantity());
        spaceship.setB5_h12_quantity(requestDto.getB5_h12_quantity());
        spaceship.setB12_h12_quantity(requestDto.getB12_h12_quantity());
        spaceship.setB10_h14_quantity(requestDto.getB10_h14_quantity());
        Long department_id = requestDto.getDepartment_id();
        if (department_id == null){
            log.info("Department id not specified.");
            return null;
        }
        Department department = departmentService.findById(department_id);
        if (department == null){
            log.info("Invalid department.");
            return null;
        }
        spaceship.setDepartment_id(department_id);
        spaceship.setIncome(requestDto.getIncome());
        return spaceshipRepo.save(spaceship);
    }

    public List<Spaceship> listall(){
        List<Spaceship> spaceshipList = spaceshipRepo.findAll();
        log.info("Listed spaceships");
        return spaceshipList;
    }
}

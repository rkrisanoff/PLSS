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

    public Spaceship update(Long id, SpaceshipUpdateRequestDto requestDto){
        if (id == null){
            log.info("Spaceship id is null.");
            return null;
        }
        Spaceship origin = spaceshipRepo.findById(id).orElse(null);
        if (origin == null){
            log.info("Spaceship {} does not exist.", id);
            return null;
        }
        Spaceship spaceship = new Spaceship();
        spaceship.setB2_h6_quantity(requestDto.getB2_h6_quantity() != null ? requestDto.getB2_h6_quantity() : origin.getB2_h6_quantity());
        spaceship.setB5_h12_quantity(requestDto.getB5_h12_quantity() != null ? requestDto.getB5_h12_quantity() : origin.getB5_h12_quantity());
        spaceship.setB12_h12_quantity(requestDto.getB12_h12_quantity() != null ? requestDto.getB12_h12_quantity() : origin.getB12_h12_quantity());
        spaceship.setB10_h14_quantity(requestDto.getB10_h14_quantity() != null ? requestDto.getB10_h14_quantity() : origin.getB10_h14_quantity());
        Long department_id = requestDto.getDepartment_id();
        if (department_id != null){
            Department department = departmentService.findById(department_id);
            if (department == null){
                log.info("Invalid department. Setting old department id");
                spaceship.setDepartment_id(origin.getDepartment_id());
            } else {
                spaceship.setDepartment_id(requestDto.getDepartment_id());
            }
        } else {
            spaceship.setDepartment_id(origin.getDepartment_id());
        }
        spaceship.setIncome(requestDto.getIncome() != null ? requestDto.getIncome() : origin.getIncome());
        spaceship.setId(id);
        return spaceshipRepo.save(spaceship);
    }

    public List<Spaceship> listall(){
        List<Spaceship> spaceshipList = spaceshipRepo.findAll();
        log.info("Listed spaceships");
        return spaceshipList;
    }

    public Spaceship findById(Long id){
        if (id == null){
            log.info("Spaceship id is null.");
            return null;
        }
        Spaceship spaceship = spaceshipRepo.findById(id).orElse(null);
        if (spaceship == null){
            log.info("Invalid spaceship id {}", id);
            return null;
        }
        return spaceship;
    }

    public Spaceship udpateSpaceship(Long id, Spaceship spaceship){
        if (id == null){
            log.info("Spaceship id is null.");
            return null;
        }
        Spaceship origin = spaceshipRepo.findById(id).orElse(null);
        if (origin == null){
            log.info("Invlid spaceship id " + id);
            return null;
        }
        Spaceship newShip = new Spaceship();
        newShip.setIncome(spaceship.getIncome() != null ? spaceship.getIncome() : origin.getIncome());
        newShip.setB12_h12_quantity(spaceship.getB12_h12_quantity() != null ? spaceship.getB12_h12_quantity() : origin.getB12_h12_quantity());
        newShip.setB10_h14_quantity(spaceship.getB10_h14_quantity() != null ? spaceship.getB10_h14_quantity() : origin.getB10_h14_quantity());
        newShip.setB5_h12_quantity(spaceship.getB5_h12_quantity() != null ? spaceship.getB5_h12_quantity() : origin.getB5_h12_quantity());
        newShip.setB2_h6_quantity(spaceship.getB2_h6_quantity() != null ? spaceship.getB2_h6_quantity() : origin.getB2_h6_quantity());

        if (spaceship.getDepartment_id() != null){
            Department department = departmentService.findById(spaceship.getDepartment_id());
            if (department == null){
                log.info("Invalid deparmtnet id " + spaceship.getDepartment_id());
                return null;
            }
            newShip.setDepartment_id(spaceship.getDepartment_id());
        } else {
            newShip.setDepartment_id(origin.getDepartment_id());
        }
        return spaceshipRepo.save(newShip);
    }
}

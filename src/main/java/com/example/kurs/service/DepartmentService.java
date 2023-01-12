package com.example.kurs.service;

import com.example.kurs.entity.Department;
import com.example.kurs.repo.DepartmentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public Department findById(Long id){
        Department department = departmentRepo.findById(id).orElse(null);
        if (department != null){
            log.info("Department {} found.", id);
        } else {
            log.info("Department {} not found.", id);
        }
        return department;
    }

    public List<Department> findAll(){
        List<Department> departments = departmentRepo.findAll();
        log.info("Listed departments.");
        return departments;
    }

    public Department update(Long id, Department department){
        if (id == null){
            log.info("Department id is null.");
            return null;
        }
        Department origin = departmentRepo.findById(id).orElse(null);
        if (origin == null){
            log.info("Invalid department id.");
            return null;
        }
        Department newDep = new Department();
        newDep.setCurrent_resource(department.getCurrent_resource() != null ? department.getCurrent_resource() : origin.getCurrent_resource());
        newDep.setExtracted_bor_quantity(department.getExtracted_bor_quantity() != null ? department.getExtracted_bor_quantity() : origin.getExtracted_bor_quantity());
        newDep.setId(id);
        return departmentRepo.save(newDep);
    }
}

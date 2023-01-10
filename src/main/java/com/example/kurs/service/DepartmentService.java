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
            log.info("Department {} not found.", id);
        } else {
            log.info("Department {} found.", id);
        }
        return department;
    }

    public List<Department> findAll(){
        List<Department> departments = departmentRepo.findAll();
        log.info("Listed departments.");
        return departments;
    }
}

package com.example.kurs.service;

import com.example.kurs.entity.Employee;
import com.example.kurs.entity.Role;
import com.example.kurs.exceptions.EmployeeAlreadyExistsException;
import com.example.kurs.repo.EmployeeRepo;
import com.example.kurs.repo.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Employee register(Employee employee){
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        if (employeeRepo.findByUsername(employee.getUsername()) != null){
            log.info("Employee {} not registered. Already exists.", employee.getUsername());
            return null;
        }
        Employee registered_employee = employeeRepo.save(employee);
        log.info("Registered employee {}.", registered_employee.getUsername());
        return registered_employee;
    }

    public List<Employee> getAll() {
        List<Employee> result = (List<Employee>) employeeRepo.findAll();
        return result;
    }

    public Employee findByUsername(String username){
        Employee employee = employeeRepo.findByUsername(username);
        if (employee == null){
            log.info("Employee {} not found.", username);
            return null;
        }
        log.info("Found employee {}.", employee.getUsername());
        return employee;
    }
    public Employee getById(Long id){
        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent()){
            log.info("Employee with id {} not found.", id);
        }
        log.info("Found employee with id {}.", id);
        return employee.get();
    }

    public void deleteById(Long id){
        employeeRepo.deleteById(id);
        log.info("Employee with id {} deleted.", id);
    }
}

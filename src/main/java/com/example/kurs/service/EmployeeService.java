package com.example.kurs.service;

import com.example.kurs.entity.Employee;
import com.example.kurs.entity.Post;
import com.example.kurs.entity.Role;
import com.example.kurs.exceptions.EmployeeAlreadyExistsException;
import com.example.kurs.repo.EmployeeRepo;
import com.example.kurs.repo.PostRepo;
import com.example.kurs.repo.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private PostRepo postRepo;
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
        List<Post> posts = postRepo.findByEmployeeId(employee.getId());
        if (posts != null){
            log.info("Found {} posts of employee {}", posts.size(), username);
            List<Role> roles = posts.stream()
                    .map(post -> roleRepo.findById(post.getRoleId()).orElse(null))
                    .filter(role -> role != null)
                    .collect(Collectors.toList());
            log.info("Found {} roles of employee {}", roles.size(), username);
            employee.setRoles(roles);
        }
        if (employee == null){
            log.info("Employee {} not found.", username);
            return null;
        }
        log.info("Found employee {}.", employee.getUsername());
        return employee;
    }
    public Employee update(Employee employee){
        if (employeeRepo.findById(employee.getId()).orElse(null) != null){
            employeeRepo.save(employee);
            log.info("Updated employee {}.", employee.getUsername());
            return employee;
        } else {
            log.info("Employee {} could not be updated, because it does not exist.", employee.getUsername());
            return null;
        }
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

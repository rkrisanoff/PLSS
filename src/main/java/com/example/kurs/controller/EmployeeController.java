package com.example.kurs.controller;

import com.example.kurs.entity.Employee;
import com.example.kurs.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/{username}")
    public ResponseEntity getAll(@PathVariable String username){
        Employee employee = employeeService.findByUsername(username);
        return ResponseEntity.ok(employee.getId());
    }
}

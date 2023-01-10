package com.example.kurs.controller;

import com.example.kurs.entity.Department;
import com.example.kurs.service.DepartmentService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private JsonProvider jsonProvider;
    @GetMapping("/all")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<Department> departments = departmentService.findAll();
        return ResponseEntity.ok(jsonProvider.convertToJson(departments));
    }
}

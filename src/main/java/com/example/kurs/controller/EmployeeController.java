package com.example.kurs.controller;

import com.example.kurs.dto.RegistrationRequestDto;
import com.example.kurs.entity.Employee;
import com.example.kurs.service.EmployeeService;
import com.example.kurs.service.RoleService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JsonProvider jsonProvider;
    @GetMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable Long id){
        employeeService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/{id}/update")
    public ResponseEntity update(@PathVariable Long id, @RequestBody RegistrationRequestDto updateDto){
        Employee employee = new Employee();
        Employee origin = employeeService.getById(id);
        if (origin == null){
            return ResponseEntity.badRequest().body("Employee " + employee.getUsername() + " was not updated because it does not exist");
        }
        employee.setUsername(updateDto.getUsername() != null ? updateDto.getUsername() : origin.getUsername());
        employee.setPassword(updateDto.getPassword() != null ? updateDto.getPassword() : origin.getPassword());
        employee.setAge(updateDto.getAge() != null ? updateDto.getAge() : origin.getAge());
        employee.setRoles(List.of(roleService.findByName("operator")));
        employee.setFirst_name(updateDto.getFirst_name() != null ? updateDto.getFirst_name() : origin.getFirst_name());
        employee.setLast_name(updateDto.getLast_name() != null ? updateDto.getLast_name() : origin.getLast_name());
        employee.setPatronymic(updateDto.getPatronymic() != null ? updateDto.getPatronymic() : origin.getPatronymic());
        employee.setId(id);
        Employee updated = employeeService.update(employee);
        return ResponseEntity.ok("Updated");
    }

    @GetMapping("/all")
    public ResponseEntity listall() throws JsonProcessingException {
        List<Employee> employees = employeeService.getAll();
        String json = jsonProvider.convertToJson(employees);
        return ResponseEntity.ok(json);
    }
}

package com.example.kurs.controller;

import com.example.kurs.dto.TaskRequestDto;
import com.example.kurs.entity.Employee;
import com.example.kurs.entity.Task;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.EmployeeService;
import com.example.kurs.service.TaskService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JsonProvider jsonProvider;

    @PostMapping("/create")
    public ResponseEntity create(@RequestHeader("Authorization") String authHeader, @RequestBody TaskRequestDto taskRequestDto){
        Task task = new Task();
        if (taskRequestDto.getCost() == null){
            return ResponseEntity.badRequest().body("Cost not specified");
        }
        task.setCost(taskRequestDto.getCost());
        task.setDescription(taskRequestDto.getDescription());
        String token = authHeader.substring(7, authHeader.length());
        Employee creator = employeeService.findByUsername(jwtTokenProvider.getUsername(token));
        task.setCreator_post_id(creator.getId());
        task.setExecutor_post_id(taskRequestDto.getExecutor_post_id());
        task.setState(taskRequestDto.getState() != null ? taskRequestDto.getState() : "unknown");
        Task created = taskService.create(task);
        if (created != null){
            return ResponseEntity.ok("Created.");
        } else {
            return ResponseEntity.badRequest().body("Some of task fields are null.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity listall() throws JsonProcessingException {
        List<Task> tasks = taskService.findAll();
        String json = jsonProvider.convertToJson(tasks);
        return ResponseEntity.ok(json);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto){
        Task updated = taskService.update(id, taskRequestDto);
        if (updated != null){
            return ResponseEntity.ok("Updated.");
        } else {
            return ResponseEntity.badRequest().body("Task " + id + " does not exist.");
        }
    }
}

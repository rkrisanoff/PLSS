package com.example.kurs.controller;

import com.example.kurs.dto.TaskRequestDto;
import com.example.kurs.entity.Employee;
import com.example.kurs.entity.Post;
import com.example.kurs.entity.Role;
import com.example.kurs.entity.Task;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.EmployeeService;
import com.example.kurs.service.PostService;
import com.example.kurs.service.RoleService;
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
    private PostService postService;

    @Autowired
    private RoleService roleService;

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
        Post managerPost = postService.findManagerPostByEmployeeId(creator.getId());
        if (managerPost != null){
            return ResponseEntity.badRequest().body("Employee " + creator.getId() + " has no manager authority to create tasks.");
        }
        task.setCreatorPostId(managerPost.getId());
        task.setExecutorPostId(taskRequestDto.getExecutorPostId());
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
        if (taskRequestDto.getCreatorPostId() != null){
            Post newCreatorPost = postService.findById(taskRequestDto.getCreatorPostId());
            if (newCreatorPost == null){
                return ResponseEntity.badRequest().body("Invalid creator post id " + taskRequestDto.getCreatorPostId());
            }
            Role newCreatorRole = roleService.findById(newCreatorPost.getRoleId());
            if (newCreatorRole.getName() != "manager"){
                return ResponseEntity.badRequest().body("New creator post " + taskRequestDto.getCreatorPostId() + " does not have manager authority.");
            }
        }
        Task updated = taskService.update(id, taskRequestDto);
        if (updated != null){
            return ResponseEntity.ok("Updated.");
        } else {
            return ResponseEntity.badRequest().body("Task " + id + " does not exist.");
        }
    }
}

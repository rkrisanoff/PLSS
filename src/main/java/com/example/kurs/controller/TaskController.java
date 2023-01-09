package com.example.kurs.controller;

import com.example.kurs.dto.TaskRequestDto;
import com.example.kurs.entity.Task;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.TaskService;
import com.example.kurs.utils.JsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
/*
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskRequestDto taskRequestDto){
        Task task = new Task();
        if (taskRequestDto.getCreator_post_id() == null){
            return ResponseEntity.badRequest().body("Creator post ")
        }
    }*/
}

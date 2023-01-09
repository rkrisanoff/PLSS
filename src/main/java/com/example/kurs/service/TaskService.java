package com.example.kurs.service;

import com.example.kurs.entity.Task;
import com.example.kurs.repo.TaskRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    public Task findById(Long id){
        Task task = taskRepo.findById(id).orElse(null);
        if (task == null){
            log.info("No task {} found.", id);
        }
        return task;
    }
}

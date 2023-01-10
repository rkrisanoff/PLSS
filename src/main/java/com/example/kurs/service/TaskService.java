package com.example.kurs.service;

import com.example.kurs.dto.TaskRequestDto;
import com.example.kurs.entity.Task;
import com.example.kurs.repo.TaskRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Task create(Task task){
        if (task.getId() != null && taskRepo.findById(task.getId()).orElse(null) != null){
            log.info("Task {} already exists", task.getId());
            return null;
        }
        if (task.getCost() == null){
            log.info("Task {} does not have cost");
            return null;
        }
        if (task.getCreator_post_id() == null){
            log.info("Task {} does not have creator");
            return null;
        }
        return taskRepo.save(task);
    }

    public List<Task> findAll() {
        List<Task> tasks = taskRepo.findAll();
        log.info("Listed all tasks.");
        return tasks;
    }

    public Task update(Long id, TaskRequestDto task){
        Task origin = taskRepo.findById(id).orElse(null);
        if (origin == null){
            return null;
        }
        Task newTask = new Task();
        newTask.setState(task.getState() != null ? task.getState() : origin.getState());
        newTask.setCost(task.getCost() != null ? task.getCost() : origin.getCost());
        newTask.setDescription(task.getDescription() != null ? task.getDescription() : origin.getDescription());
        newTask.setExecutor_post_id(task.getExecutor_post_id() != null ? task.getExecutor_post_id() : origin.getExecutor_post_id());
        newTask.setCreator_post_id(task.getCreator_post_id() != null ? task.getCreator_post_id() : origin.getCreator_post_id());
        newTask.setId(id);
        return taskRepo.save(newTask);
    }
}

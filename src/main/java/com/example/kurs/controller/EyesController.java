package com.example.kurs.controller;

import com.example.kurs.entity.EyesSensors;
import com.example.kurs.repo.EyesRepo;
import com.example.kurs.service.EyesService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eyes")
public class EyesController {
    @Autowired
    private EyesService eyesService;
    @Autowired
    private JsonProvider jsonProvider;
    @GetMapping("/all")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<EyesSensors> eyes = eyesService.listall();
        return ResponseEntity.ok(jsonProvider.convertToJson(eyes));
    }
}

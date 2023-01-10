package com.example.kurs.controller;

import com.example.kurs.entity.PositronicBrain;
import com.example.kurs.service.BrainService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brains")
public class BrainController {
    @Autowired
    private BrainService brainService;
    @Autowired
    private JsonProvider jsonProvider;
    @GetMapping("/all")
    public ResponseEntity getALl() throws JsonProcessingException {
        List<PositronicBrain> brains = brainService.findAll();
        return ResponseEntity.ok(jsonProvider.convertToJson(brains));
    }
}

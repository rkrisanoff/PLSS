package com.example.kurs.controller;

import com.example.kurs.entity.MicroreactorType;
import com.example.kurs.repo.ReactorRepo;
import com.example.kurs.service.ReactorService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/microreactors")
public class ReactorController {
    @Autowired
    private ReactorService reactorService;
    @Autowired
    private JsonProvider jsonProvider;
    @GetMapping("/all")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<MicroreactorType> reactors = reactorService.findAll();
        return ResponseEntity.ok(jsonProvider.convertToJson(reactors));
    }
}

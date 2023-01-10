package com.example.kurs.controller;

import com.example.kurs.entity.Body;
import com.example.kurs.service.BodyService;
import com.example.kurs.utils.JsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/body")
public class BodyController {
    @Autowired
    private BodyService bodyService;
    @Autowired
    private JsonProvider jsonProvider;


}

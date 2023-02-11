package com.example.kurs.controller;

import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
//    @Autowired
//    private RoleService;
    @GetMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}

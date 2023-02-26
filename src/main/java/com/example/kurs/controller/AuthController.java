package com.example.kurs.controller;

import com.example.kurs.dto.SigninDto;
import com.example.kurs.dto.SignupDto;
import com.example.kurs.entity.User;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;


    @PostMapping("/signin")
    public ResponseEntity singin(@Valid @RequestBody SigninDto signinDto) {
        String token = userService.singin(signinDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody SignupDto signupDto) throws Exception {
        userService.register(signupDto);
        return ResponseEntity.ok("");
    }
}

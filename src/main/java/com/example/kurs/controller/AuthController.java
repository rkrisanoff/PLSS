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
    public ResponseEntity singin(@RequestBody SigninDto signinDto) {
        String username = signinDto.getUsername();
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, signinDto.getPassword()));
        String token = jwtTokenProvider.createToken(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupDto signupDto) throws Exception {
        userService.register(signupDto);
        return ResponseEntity.ok("");
    }
}

package com.example.kurs.controller;

import com.example.kurs.dto.SigninDto;
import com.example.kurs.dto.SignupDto;
import com.example.kurs.entity.Role;
import com.example.kurs.entity.User;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;


    @PostMapping("/signin")
    public ResponseEntity singin(@RequestBody SigninDto signinDto){
        try{
            String username = signinDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, signinDto.getPassword()));
            User user = userService.getByUsername(username);
            if (user == null){
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }
            String token = jwtTokenProvider.createToken(user.getId(), username, Collections.singletonList(user.getRole()));
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupDto signupDto){
        try{
            String username = signupDto.getUsername();
            String password = signupDto.getPassword();
            String email = signupDto.getEmail();

            User registerUser = new User();
            registerUser.setUsername(username);
            registerUser.setPassword(password);
            registerUser.setEmail(email);
            registerUser.setRole(Role.USER);
            User registered = userService.register(registerUser);

            if (registered == null){
                return ResponseEntity.badRequest().body("User " + username + " already exists");
            }
            return ResponseEntity.ok("Registered");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Some unchecked error occurred");
        }
    }

}

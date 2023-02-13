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
            Optional<String> username = Optional.ofNullable(signupDto.getUsername());
            Optional<String> password = Optional.ofNullable(signupDto.getPassword());
            Optional<String> email = Optional.ofNullable(signupDto.getEmail());
            if (!(username.isPresent() || password.isPresent() || email.isPresent())){
                return ResponseEntity.status(421).body("Incorrect username or password");
            }

            User registerUser = new User();
            registerUser.setUsername(username.get());
            registerUser.setPassword(password.get());
            registerUser.setEmail(email.get());
            registerUser.setRole(Role.USER);
            User registered = userService.register(registerUser);

            if (registered == null){
                return ResponseEntity.status(422).body("User " + username + " already exists");
            }
            return ResponseEntity.ok("Registered");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Some unchecked error occurred");
        }
    }

}

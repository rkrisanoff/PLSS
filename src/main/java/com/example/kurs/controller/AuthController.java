package com.example.kurs.controller;

import com.example.kurs.dto.AuthenticationRequestDto;
import com.example.kurs.dto.RegistrationRequestDto;
import com.example.kurs.entity.Employee;
import com.example.kurs.exceptions.EmployeeAlreadyExistsException;
import com.example.kurs.repo.EmployeeRepo;
import com.example.kurs.security.jwt.JwtTokenProvider;
import com.example.kurs.service.EmployeeService;
import com.example.kurs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
        try{
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            Employee employee = employeeService.findByUsername(username);
            if (employee == null){
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }
            String token = jwtTokenProvider.createToken(username, employee.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequestDto requestDto){
        try{
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();
            String password_confirmation = requestDto.getPassword_confirmation();
            if (!password.equals(password_confirmation)){
                return ResponseEntity.badRequest().body("Password doesn't match password confirmation");
            }
            Employee registerEmployee = new Employee();
            registerEmployee.setUsername(username);
            registerEmployee.setPassword(password);
            registerEmployee.setAge(requestDto.getAge());
            registerEmployee.setRoles(List.of(roleService.findByName("operator")));
            registerEmployee.setFirst_name(requestDto.getFirst_name());
            registerEmployee.setLast_name(requestDto.getLast_name());
            registerEmployee.setPatronymic(requestDto.getPatronymic());

            Employee registered = employeeService.register(registerEmployee);
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

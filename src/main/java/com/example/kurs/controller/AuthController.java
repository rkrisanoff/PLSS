package com.example.kurs.controller;

import com.example.kurs.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    private RoleService roleService;
//

//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
//        try{
//            String username = requestDto.getUsername();
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
//            Employee employee = employeeService.findByUsername(username);
//            if (employee == null){
//                throw new UsernameNotFoundException("User with username " + username + " not found");
//            }
//            String token = jwtTokenProvider.createToken(employee.getId(), username, employee.getRoles());
//            Map<Object, Object> response = new HashMap<>();
//            response.put("username", username);
//            response.put("token", token);
//            return ResponseEntity.ok(response);
//        } catch (AuthenticationException e){
//            throw new BadCredentialsException("Invalid username or password");
//        }
//    }

//    }

}

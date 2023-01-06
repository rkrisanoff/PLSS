package com.example.kurs.security;

import com.example.kurs.entity.Employee;
import com.example.kurs.security.jwt.JwtUser;
import com.example.kurs.security.jwt.JwtUserFactory;
import com.example.kurs.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeService.findByUsername(username);
        if (employee == null){
            throw new UsernameNotFoundException("Employee with username " + username + " not found.");
        }

        JwtUser jwtUser = JwtUserFactory.create(employee);
        log.info("loadUserByUsername - user with username {} successfully loaded.", username);
        return jwtUser;
    }
}

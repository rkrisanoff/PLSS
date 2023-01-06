package com.example.kurs.security.jwt;

import com.example.kurs.entity.Employee;
import com.example.kurs.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(Employee employee){
        return new JwtUser(
                employee.getId(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getFirst_name(),
                employee.getLast_name(),
                employee.getPatronymic(),
                mapToGrantedAuthorities(employee.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> employeeRoles){
        return employeeRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

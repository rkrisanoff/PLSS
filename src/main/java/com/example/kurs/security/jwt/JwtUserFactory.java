package com.example.kurs.security.jwt;

import com.example.kurs.entity.Role;
import com.example.kurs.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(Collections.singletonList(user.getRole()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> employeeRoles) {
        return employeeRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

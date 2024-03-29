package com.example.kurs.service;

import com.example.kurs.entity.Role;
import com.example.kurs.repo.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public Role findByName(String name){
        return roleRepo.findByName(name);
    }
    public Role findById(Long id){
        Role role = roleRepo.findById(id).orElse(null);
        if (role != null){
            log.info("Role {} found.", id);
        } else {
            log.info("Role {} not found.", id);
        }
        return role;
    }
}

package com.example.kurs.service;

import com.example.kurs.entity.User;
import com.example.kurs.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepo.findByUsername(user.getUsername()) != null){
            log.info("Employee {} not registered. Already exists.", user.getUsername());
            return null;
        }
        User registered_user = userRepo.save(user);
        log.info("Registered employee {}.", registered_user.toString());
        return registered_user;
    }

    public List<User> getAll() {
        List<User> result = (List<User>) userRepo.findAll();
        return result;
    }


    public User getById(Long id){
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()){
            log.info("User with id {} not found.", id);
            return null;
        }
        log.info("Found user with id {}.", id);
        return user.get();

    }

    public User getByUsername(String username){
        Optional<User> user = Optional.ofNullable(userRepo.findByUsername(username));
        if (!user.isPresent()){
            log.info("User with username {} not found.", username);
            return null;
        }
        log.info("Found user with username {}.", username);
        return user.get();

    }

    public void deleteById(Long id){
        userRepo.deleteById(id);
        log.info("Employee with id {} deleted.", id);
    }
}

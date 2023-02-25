package com.example.kurs.service;

import com.example.kurs.dto.SignupDto;
import com.example.kurs.entity.Role;
import com.example.kurs.entity.User;
import com.example.kurs.exceptions.UserAlreadyExistsException;
import com.example.kurs.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void register(SignupDto signupDto) throws Exception {

        Optional<String> username = Optional.ofNullable(signupDto.getUsername());
        if (!username.isPresent()) {
            throw new Exception("Username {} doesn't present");
        }
        Optional<String> password = Optional.ofNullable(signupDto.getPassword());
        if (!password.isPresent()) {
            throw new Exception("Password {} doesn't present");
        }
        Optional<String> email = Optional.ofNullable(signupDto.getEmail());
        if (!email.isPresent()) {
            throw new Exception("Email {} doesn't present");
        }

        User user = new User();
        user.setUsername(username.get());
        user.setEmail(email.get());
        user.setRole(Role.USER);

        user.setPassword(passwordEncoder.encode(password.get()));

        if (userRepo.findByUsername(user.getUsername()) != null) {
            log.info("User {} registered. Already exists.", user.getUsername());
            throw new UserAlreadyExistsException("User {} registered. Already exists "+user.getUsername());
        }
        User registered_user = userRepo.save(user);
        log.info("Registered user {}.", registered_user);
    }


    public User getByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepo.findByUsername(username));
        if (!user.isPresent()) {
            log.info("User with username {} not found.", username);
            return null;
        }
        log.info("Found user with username {}.", username);
        return user.get();
    }


    public User getById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
            log.info("User with id {} not found.", id);
            return null;
        }
        log.info("Found user with id {}.", id);
        return user.get();

    }

    public Boolean existsById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
            log.info("User with id {} not found.", id);
            return false;
        }
        log.info("Found user with id {}.", id);
        return true;

    }
}

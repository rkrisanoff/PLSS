package com.example.kurs.service;

import com.example.kurs.entity.MicroreactorInSpaceship;
import com.example.kurs.entity.MicroreactorType;
import com.example.kurs.repo.ReactorInShipRepo;
import com.example.kurs.repo.ReactorRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReactorService {
    @Autowired
    ReactorRepo reactorRepo;
    @Autowired
    ReactorInShipRepo reactorInShipRepo;

    public MicroreactorType findById(Long id){
        MicroreactorType reactor = reactorRepo.findById(id).orElse(null);
        if (reactor != null){
            log.info("Reactor {} found.", id);
        } else {
            log.info("Reactor {} not found.", id);
        }
        return reactor;
    }

    public List<MicroreactorType> findBySpaceshipId(Long id){
        if (id == null){
            log.info("Spaceship id is null.");
            return null;
        }
        log.info("Listed reactors in {} spaceship.", id);
        return reactorInShipRepo.findBySpaceshipId(id).stream()
                .map(reactorInShip -> reactorRepo.findById(reactorInShip.getMicroreactorTypeId()).get())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<MicroreactorType> findAll() {
        List<MicroreactorType> reactors = reactorRepo.findAll();
        log.info("Listed all reactors.");
        return reactors;
    }
}

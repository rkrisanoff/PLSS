package com.example.kurs.repo;

import com.example.kurs.entity.MicroreactorInSpaceship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactorInShipRepo extends JpaRepository<MicroreactorInSpaceship, Long> {
    List<MicroreactorInSpaceship> findBySpaceshipId(Long id);
}

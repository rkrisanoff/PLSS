package com.example.kurs.repo;

import com.example.kurs.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findByEmployeeId(Long id);
}

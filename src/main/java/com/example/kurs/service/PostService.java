package com.example.kurs.service;

import com.example.kurs.entity.Post;
import com.example.kurs.repo.PostRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostService {
    @Autowired
    private PostRepo postRepo;
    public Post createPost(Post post){
        if (post.getId() == null || postRepo.findById(post.getId()).orElse(null) == null){
            postRepo.save(post);
            log.info("Created post, employee: {}, role: {}.", post.getEmployeeId(), post.getRoleId());
            return post;
        }
        log.info("Post {} already exists.", post.getId());
        return null;
    }

    public Post findById(Long id){
        Post post = postRepo.findById(id).orElse(null);
        if (post != null){
            log.info("Found post {}", id);
        } else {
            log.info("Post {} not found.", id);
        }
        return post;
    }
    public List<Post> getAll(){
        List<Post> posts = postRepo.findAll();
        log.info("Listed posts");
        return posts;
    }

    public void deleteById(Long id){
        postRepo.deleteById(id);
        log.info("Deleted post {}", id);
    }

    public Post update(Post post){
        if (post.getId() != null && postRepo.findById(post.getId()).orElse(null) != null){
            postRepo.save(post);
            log.info("Updated post {}", post.getId());
            return post;
        }
        log.info("Post {} does not exist", post.getId());
        return null;
    }
}

package com.example.kurs.service;

import com.example.kurs.entity.Post;
import com.example.kurs.repo.PostRepo;
import com.example.kurs.repo.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private RoleRepo roleRepo;
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
    public Post findManagerPostByEmployeeId(Long id){
        List<Post> posts = postRepo.findByEmployeeId(id);
        List<Post> managerPosts = posts.stream()
                .filter(post -> roleRepo.findById(post.getRoleId()).get().getName() == "manager")
                .collect(Collectors.toList());
        if (managerPosts == null || managerPosts.size() <= 0) {
            log.info("Employee {} does not have manager roles.");
            return null;
        }
        return managerPosts.get(0);
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

    public Post findOperatorPostByEmployeeId(Long id) {
        List<Post> posts = postRepo.findByEmployeeId(id);
        List<Post> managerPosts = posts.stream()
                .filter(post -> roleRepo.findById(post.getRoleId()).get().getCan_operate_robot())
                .collect(Collectors.toList());
        if (managerPosts == null || managerPosts.size() <= 0) {
            log.info("Employee {} does not have manager roles.");
            return null;
        }
        return managerPosts.get(0);
    }
}

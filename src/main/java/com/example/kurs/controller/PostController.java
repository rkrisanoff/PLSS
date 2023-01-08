package com.example.kurs.controller;

import com.example.kurs.dto.PostDto;
import com.example.kurs.entity.Employee;
import com.example.kurs.entity.Post;
import com.example.kurs.entity.Role;
import com.example.kurs.service.EmployeeService;
import com.example.kurs.service.PostService;
import com.example.kurs.service.RoleService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;
    @Autowired
    PostService postService;
    @Autowired
    JsonProvider jsonProvider;

    @PostMapping("/all")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<Post> posts = postService.getAll();
        String json = jsonProvider.convertToJson(posts);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable Long id){
        postService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/{id}/update")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PostDto postDto){
        Post post = new Post();
        Post origin = postService.findById(id);
        if (origin == null){
            return ResponseEntity.badRequest().body("Could not update post " + id + ", because it does not exist");
        }
        Role role = roleService.findByName(postDto.getRolename());
        Employee employee = employeeService.findByUsername(postDto.getEmployee_username());
        post.setRoleId(role != null ? role.getId() : origin.getRoleId());
        post.setEmployeeId(employee != null ? employee.getId() : origin.getEmployeeId());
        post.setDepartmentId(postDto.getDepartment_id() != null ? postDto.getDepartment_id() : origin.getDepartmentId());
        post.setPremium(postDto.getPremium() != null ? postDto.getPremium() : origin.getPremium());
        post.setId(origin.getId());
        Post updated = postService.update(post);
        return ResponseEntity.ok("Updated");
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PostDto postDto){
        Post post = new Post();
        Role role = roleService.findByName(postDto.getRolename());
        Employee employee = employeeService.findByUsername(postDto.getEmployee_username());
        post.setRoleId(role.getId());
        post.setEmployeeId(employee.getId());
        post.setDepartmentId(postDto.getDepartment_id());
        post.setPremium(postDto.getPremium());
        postService.createPost(post);
        return ResponseEntity.ok("Created");
    }
}

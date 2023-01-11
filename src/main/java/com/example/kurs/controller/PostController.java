package com.example.kurs.controller;

import com.example.kurs.dto.PostDto;
import com.example.kurs.entity.Department;
import com.example.kurs.entity.Employee;
import com.example.kurs.entity.Post;
import com.example.kurs.entity.Role;
import com.example.kurs.repo.PostRepo;
import com.example.kurs.service.DepartmentService;
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
    @Autowired
    DepartmentService departmentService;
    @Autowired
    private PostRepo postRepo;

    @GetMapping("/all")
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
        if (postDto.getEmployee_username() != null){
            Employee employee = employeeService.findByUsername(postDto.getEmployee_username());
            if (employee != null){
                post.setEmployeeId(employee.getId());
            } else {
                return ResponseEntity.badRequest().body("Employee " + postDto.getEmployee_username() + " not found.");
            }
        } else {
            post.setEmployeeId(origin.getEmployeeId());
        }
        if (postDto.getRolename() != null){
            Role tempRole = roleService.findByName(postDto.getRolename());
            Post managerPost = postService.findManagerPostByEmployeeId(id);
            Post operatorPost = postService.findOperatorPostByEmployeeId(id);
            if (tempRole == null){
                return ResponseEntity.badRequest().body("Invalid role name " + postDto.getRolename());
            }
            if (managerPost != null && postDto.getRolename() == "manager"){
                return ResponseEntity.badRequest().body("Employee " + post.getEmployeeId() + " already has manager post.");
            }
            if (operatorPost != null && postDto.getRolename() == "operator"){
                return ResponseEntity.badRequest().body("Employee " + post.getEmployeeId() + " already has operator post.");
            }
            post.setRoleId(tempRole.getId());
        } else {
            post.setRoleId(origin.getRoleId());
        }

        if (postDto.getDepartment_id() != null){
            Department department = departmentService.findById(postDto.getDepartment_id());
            if (department != null){
                post.setDepartmentId(postDto.getDepartment_id());
            } else {
                return ResponseEntity.badRequest().body("Department " + post.getDepartmentId() + " not found.");
            }
        } else {
            post.setDepartmentId(origin.getDepartmentId());
        }
        post.setPremium(postDto.getPremium() != null ? postDto.getPremium() : origin.getPremium());
        post.setId(origin.getId());
        Post updated = postService.update(post);
        return ResponseEntity.ok("Updated");
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PostDto postDto){
        Post post = new Post();
        if (postDto.getRolename() == null){
            return ResponseEntity.badRequest().body("Role name not specified");
        }
        if (postDto.getDepartment_id() == null){
            return ResponseEntity.badRequest().body("Department id not specified");
        }
        if (postDto.getEmployee_username() == null){
            return ResponseEntity.badRequest().body("Employee username not specified");
        }

        Role role = roleService.findByName(postDto.getRolename());
        if (role == null){
            return ResponseEntity.badRequest().body("Invalid role " + postDto.getRolename());
        }
        Employee employee = employeeService.findByUsername(postDto.getEmployee_username());
        if (employee == null){
            return ResponseEntity.badRequest().body("Invalid employee username " + postDto.getEmployee_username());
        }
        Post managerPost = postService.findManagerPostByEmployeeId(employee.getId());
        if (managerPost != null && postDto.getRolename() == "manager"){
            return ResponseEntity.badRequest().body("Employee " + employee.getId() + " already has manager role.");
        }
        Post robotOperatorPost = postService.findOperatorPostByEmployeeId(employee.getId());
        if (robotOperatorPost != null && roleService.findByName(postDto.getRolename()).getCan_operate_robot()){
            return ResponseEntity.badRequest().body("Employee " + employee.getId() + " already has post with operating robot authority.");
        }
        Department department = departmentService.findById(postDto.getDepartment_id());
        if (department == null){
            return ResponseEntity.badRequest().body("Invalid department id " + postDto.getDepartment_id());
        }
        post.setRoleId(role.getId());
        post.setEmployeeId(employee.getId());
        post.setDepartmentId(postDto.getDepartment_id());
        post.setPremium(postDto.getPremium());
        postService.createPost(post);
        return ResponseEntity.ok("Created");
    }
}

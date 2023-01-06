package com.example.kurs.repo;

import com.example.kurs.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {
    Employee findByUsername(String username);
}

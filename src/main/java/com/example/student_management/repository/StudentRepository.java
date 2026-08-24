package com.example.student_management.repository;

import com.example.student_management.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository
        extends MongoRepository<Student, Long> {

    Optional<Student> findByRollNo(String rollNo);

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByDepartmentIgnoreCase(String department);
}
package com.example.student_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student_management.model.Student;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Optional<Student> findByRollNo(String rollNo);

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByDepartmentIgnoreCase(String department);
}
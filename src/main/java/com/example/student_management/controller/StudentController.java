package com.example.student_management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management.model.Student;
import com.example.student_management.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    // Get student by ID
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    // Get student by roll number
    @GetMapping("/roll/{rollNo}")
    public Student getByRollNo(
            @PathVariable String rollNo) {

        return service.getStudentByRollNo(rollNo);
    }

    // Search student by name
    @GetMapping("/search")
    public List<Student> search(
            @RequestParam String name) {

        return service.searchByName(name);
    }

    // Add student
    @PostMapping
    public Student addStudent(
            @RequestBody Student student) {

        return service.addStudent(student);
    }

    // Update student
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return service.updateStudent(id, student);
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Long id) {

        service.deleteStudent(id);

        return ResponseEntity.ok(
                "Student deleted successfully"
        );
    }

    // Count students
    @GetMapping("/count")
    public long getStudentCount() {
        return service.getStudentCount();
    }
}
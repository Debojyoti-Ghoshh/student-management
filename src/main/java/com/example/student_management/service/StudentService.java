package com.example.student_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.student_management.model.Student;
import com.example.student_management.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository;

    private final SequenceGeneratorService sequenceGeneratorService;

    public StudentService(
            StudentRepository repository,
            SequenceGeneratorService sequenceGeneratorService) {
    
        this.repository = repository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    // Add student
    public Student addStudent(Student student) {

        if (student.getId() == null) {
    
            student.setId(
                    sequenceGeneratorService.generateSequence(
                            "students_sequence"
                    )
            );
        }
    
        return repository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // Get student by ID
    public Student getStudentById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));
    }

    // Get student by roll number
    public Student getStudentByRollNo(String rollNo) {

        return repository.findByRollNo(rollNo)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));
    }

    // Search by name
    public List<Student> searchByName(String name) {

        return repository
                .findByNameContainingIgnoreCase(name);
    }

    // Update student
    public Student updateStudent(
            Long id,
            Student updatedStudent) {

        Student student = getStudentById(id);

        student.setName(updatedStudent.getName());
        student.setRollNo(updatedStudent.getRollNo());
        student.setDepartment(updatedStudent.getDepartment());
        student.setSemester(updatedStudent.getSemester());
        student.setMarks(updatedStudent.getMarks());

        return repository.save(student);
    }

    // Delete student
    public void deleteStudent(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }

        repository.deleteById(id);
    }

    // Count students
    public long getStudentCount() {
        return repository.count();
    }
}
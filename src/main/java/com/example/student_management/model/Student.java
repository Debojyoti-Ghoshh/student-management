package com.example.student_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "roll_no", nullable = false, unique = true)
    private String rollNo;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private int semester;

    @Column(nullable = false)
    private double marks;

    public Student() {
    }

    public Student(String name, String rollNo,
                   String department, int semester,
                   double marks) {

        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.semester = semester;
        this.marks = marks;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getDepartment() {
        return department;
    }

    public int getSemester() {
        return semester;
    }

    public double getMarks() {
        return marks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }
}
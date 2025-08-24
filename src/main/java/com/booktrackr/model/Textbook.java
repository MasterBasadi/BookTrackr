package com.booktrackr.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "textbook")
public class Textbook {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id // For database connection
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String course;
    private String textbookID;
    private String condition;
    private String teacher;
    private String studentName;
    private LocalDate returnDate;

    public Textbook() { // The empty constructor for using JPA
    }

    public Textbook(String course, String textbookID, String condition, String teacher) { // Constructors
        this.course = course;
        this.textbookID = textbookID;
        this.condition = condition;
        this.teacher = teacher;
        this.studentName = "Unassigned";
        this.returnDate = null;
    }

    public Textbook(String course, String textbookID, String condition, String teacher, String studentName, LocalDate returnDate) {
        this.course = course;
        this.textbookID = textbookID;
        this.condition = condition;
        this.teacher = teacher;
        this.studentName = studentName;
        this.returnDate = returnDate;
    }

    public String getCourse() {
        return course;
    } // Getters & Setters
    public void setCourse(String course) {
        this.course = course;
    }

    public String getTextbookID() {
        return textbookID;
    }
    public void setTextbookID(String textbookID) {
        this.textbookID = textbookID;
    }

    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public LocalDate getReturnDate() {return returnDate;}
    public void setReturnDate(LocalDate returnDate) {this.returnDate = returnDate;}

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() { return id;}

    @Override
    public String toString() { // toString
        return "Course: " + course + ", Textbook ID: " + textbookID + ", Condition: " + condition + ", Student Name: " + studentName + ", Return Date: " + returnDate;
    }
}
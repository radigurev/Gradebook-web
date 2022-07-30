package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.security.auth.Subject;
import java.time.LocalDateTime;

@Entity
@Table
public class Grades extends BaseEntity{
    private String grade;
    private User student;
    private User teacher;
    private LocalDateTime date;
    private String reason;
    private String type;
    private Subjects subject;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    @ManyToOne
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
    @ManyToOne
    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @ManyToOne
    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

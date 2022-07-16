package com.example.onlinegradebook.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
public class ResponseStudents extends BaseEntity {

    private Response type;
    private User student;
    private User teacher;
    private School school;
    private SubjectSchool subject;
    private String description;
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    public Response getType() {
        return type;
    }

    public void setType(Response type) {
        this.type = type;
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
    @ManyToOne
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
    @ManyToOne
    public SubjectSchool getSubject() {
        return subject;
    }

    public void setSubject(SubjectSchool subject) {
        this.subject = subject;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

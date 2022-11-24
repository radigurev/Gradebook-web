package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
public class AbsenceStudent extends BaseEntity {

    private User teacher;
    private User student;
    private Absence type;
    private LocalDateTime date;
    private School school;

    private Subjects subject;

    @ManyToOne
    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    @ManyToOne
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    @ManyToOne
    public Absence getType() {
        return type;
    }

    public void setType(Absence type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @ManyToOne
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @ManyToOne
    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }
}

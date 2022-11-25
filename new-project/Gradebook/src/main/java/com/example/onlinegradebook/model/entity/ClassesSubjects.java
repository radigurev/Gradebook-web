package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ClassesSubjects extends BaseEntity{

    private ClassesSchool classes;
    private Subjects subject;
    private User teacher;

    @ManyToOne
    public ClassesSchool getClasses() {
        return classes;
    }

    public void setClasses(ClassesSchool classes) {
        this.classes = classes;
    }

    @ManyToOne
    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    @ManyToOne
    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}

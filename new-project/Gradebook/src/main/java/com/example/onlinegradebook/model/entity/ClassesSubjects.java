package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ClassesSubjects extends BaseEntity{

    private ClassesSchool classes;
    private SubjectSchool subject;

    @ManyToOne
    public ClassesSchool getClasses() {
        return classes;
    }

    public void setClasses(ClassesSchool classes) {
        this.classes = classes;
    }
    @ManyToOne
    public SubjectSchool getSubject() {
        return subject;
    }

    public void setSubject(SubjectSchool subject) {
        this.subject = subject;
    }
}

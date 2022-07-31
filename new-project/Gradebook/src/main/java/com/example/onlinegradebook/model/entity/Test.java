package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table
public class Test extends BaseEntity {
    private User teacher;
    private ClassesSchool schoolClass;
    private Date date;
    private Subjects subject;
    private School school;

    @ManyToOne
    public User getTeacher() {
        return teacher;
    }
    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
    @ManyToOne
    public ClassesSchool getSchoolClass() {
        return schoolClass;
    }
    public void setSchoolClass(ClassesSchool schoolClass) {
        this.schoolClass = schoolClass;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @ManyToOne
    public Subjects getSubject() {
        return subject;
    }
    public void setSubject(Subjects subject) {
        this.subject = subject;
    }
    @ManyToOne
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}

package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Program extends BaseEntity{
    private String room;
    private Subjects subject;
    private User teacher;
    private String dateHours;

    private ClassesSchool classes;

    private String day;

    private String numClass;


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String getDateHours() {
        return dateHours;
    }

    public void setDateHours(String dateHours) {
        this.dateHours = dateHours;
    }

    @ManyToOne
    public ClassesSchool getClasses() {
        return classes;
    }

    public void setClasses(ClassesSchool classes) {
        this.classes = classes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNumClass() {
        return numClass;
    }

    public void setNumClass(String numClass) {
        this.numClass = numClass;
    }
}

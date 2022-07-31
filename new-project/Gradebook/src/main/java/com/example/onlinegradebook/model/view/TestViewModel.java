package com.example.onlinegradebook.model.view;

import com.example.onlinegradebook.model.entity.ClassesSchool;
import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.model.entity.User;

import java.sql.Date;

public class TestViewModel {

    private String teacher;
    private String classWithLetter;
    private Date date;
    private String subject;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassWithLetter() {
        return classWithLetter;
    }

    public void setClassWithLetter(String classWithLetter) {
        this.classWithLetter = classWithLetter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

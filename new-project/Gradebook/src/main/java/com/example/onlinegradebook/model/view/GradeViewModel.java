package com.example.onlinegradebook.model.view;

import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.model.entity.User;

import java.time.LocalDateTime;

public class GradeViewModel {
    private String grade;
    private String teacher;

    private String date;
    private String reason;
    private String type;
    private String subject;



//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    //    public User getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(User teacher) {
//        this.teacher = teacher;
//    }
//
//    public Subjects getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subjects subject) {
//        this.subject = subject;
//    }
}

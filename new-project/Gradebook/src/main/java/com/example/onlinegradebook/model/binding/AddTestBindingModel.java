package com.example.onlinegradebook.model.binding;

import com.example.onlinegradebook.model.entity.User;

public class AddTestBindingModel {
    private User teacher;
    private String date;
    private String subject;

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

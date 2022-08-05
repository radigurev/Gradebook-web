package com.example.onlinegradebook.model.binding.admin;

import javax.validation.constraints.Size;

public class AdminSubjectBindingModel {

    private String subject;

    @Size(min = 3)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
